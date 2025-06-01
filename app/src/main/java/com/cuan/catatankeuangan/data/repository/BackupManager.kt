package com.cuan.catatankeuangan.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.cuan.catatankeuangan.data.local.dao.ProductDao
import com.cuan.catatankeuangan.data.local.dao.TransactionDao
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class BackupManager(
    private val db: FirebaseFirestore,
    private val productDao: ProductDao,
    private val transactionDao: TransactionDao,
    private val cloudinaryService: CloudinaryService,
    private val context: Context
) {
    suspend fun backupProducts(userEmail: String?) {
        if (userEmail.isNullOrEmpty()) return

        val products = productDao.getAllProductsForBackup()
        val userRef = db.collection("backups").document(userEmail)
        val productCollection = userRef.collection("products")
        val transactions = transactionDao.getAllTransactionsForBackup()
        val transactionCollection = userRef.collection("transactions")

        val existing = productCollection.get().await()
        existing.documents.forEach { it.reference.delete() }
        val existingTransactions = transactionCollection.get().await()
        existingTransactions.documents.forEach { it.reference.delete() }

        for (product in products) {
            var imageUrl: String? = product.imageUri

            // Menyimpan path image local ke firebase
            if (!product.imageUri.isNullOrEmpty() && product.imageUri.startsWith("file://")) {
                val uri = Uri.parse(product.imageUri)
                imageUrl = suspendUploadImage(uri, product.id.toString())
            }

            val productMap = mapOf(
                "id" to product.id,
                "name" to product.name,
                "sellPrice" to product.sellPrice,
                "buyPrice" to product.buyPrice,
                "stock" to product.stock,
                "categoryId" to product.categoryId,
                "imageUri" to imageUrl
            )

            val docRef = productCollection.document(product.id.toString())
            docRef.set(productMap).await()
        }

        for (transaction in transactions) {
            val transactionMap = mapOf(
                "id" to transaction.id,
                "description" to transaction.description,
                "total" to transaction.total,
                "transactionType" to transaction.transactionType.name,
                "timestamp" to transaction.timestamp
            )

            transactionCollection.document(transaction.id.toString()).set(transactionMap).await()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun suspendUploadImage(uri: Uri, publicId: String): String? =
        suspendCancellableCoroutine { cont ->
            cloudinaryService.uploadImage(uri, publicId,
                onSuccess = { url -> cont.resume(url) {} },
                onError = { cont.resume(null) {} }
            )
        }

    suspend fun restoreProducts(userEmail: String?) {
        if (userEmail == null) return

        val snapshot = db.collection("backups")
            .document(userEmail)
            .collection("products")
            .get()
            .await()

        val products = snapshot.documents.mapNotNull { doc ->
            try {
                val id = doc.getLong("id")!!.toInt()
                val imageUrl = doc.getString("imageUri")

                val localImagePath = if (!imageUrl.isNullOrEmpty()) {
                    downloadAndSaveImage(imageUrl, id)
                } else null

                Product(
                    id = id,
                    name = doc.getString("name") ?: "",
                    sellPrice = doc.getLong("sellPrice") ?: 0L,
                    buyPrice = doc.getLong("buyPrice") ?: 0L,
                    stock = doc.getLong("stock")?.toInt() ?: 0,
                    categoryId = doc.getLong("categoryId")?.toInt(),
                    imageUri = localImagePath ?: imageUrl
                )
            } catch (e: Exception) {
                null
            }
        }

        productDao.insertAll(products)
    }

    private suspend fun downloadAndSaveImage(imageUrl: String, productId: Int): String? {
        return try {
            val url = URL(imageUrl)
            val connection = withContext(Dispatchers.IO) {
                url.openConnection()
            }
            withContext(Dispatchers.IO) {
                connection.connect()
            }

            val inputStream = withContext(Dispatchers.IO) {
                connection.getInputStream()
            }
            val file = File(context.filesDir, "products")
            if (!file.exists()) file.mkdirs()

            val imageFile = File(file, "$productId.jpg")
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(imageFile)
            }

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            imageFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun backupCategories(userEmail: String?) {
        if (userEmail.isNullOrEmpty()) return

        val categories = withContext(Dispatchers.IO) {
            productDao.getAllCategoriesForBackup()
        }
        if (categories.isEmpty()) return

        val userRef = db.collection("backups").document(userEmail)
        val categoryCollection = userRef.collection("categories")

        val existing = categoryCollection.get().await()
        existing.documents.forEach { it.reference.delete() }

        for (category in categories) {
            val categoryMap = mapOf(
                "id" to category.id,
                "name" to category.name
            )

            categoryCollection.document(category.id.toString()).set(categoryMap).await()
        }
    }

    suspend fun restoreCategories(userEmail: String?) {
        if (userEmail.isNullOrEmpty()) return

        val snapshot = db.collection("backups")
            .document(userEmail)
            .collection("categories")
            .get()
            .await()

        val categories = snapshot.documents.mapNotNull { doc ->
            try {
                Category(
                    id = doc.getLong("id")!!.toInt(),
                    name = doc.getString("name") ?: ""
                )
            } catch (e: Exception) {
                null
            }
        }

        productDao.insertAllCategories(categories)
    }

    suspend fun restoreTransaction(userEmail: String?) {
        val transactionSnapshot = userEmail?.let {
            db.collection("backups")
                .document(it)
                .collection("transactions")
                .get()
                .await()
        }

        val transactions = transactionSnapshot?.documents?.mapNotNull { doc ->
            try {
                Transaction(
                    id = doc.getLong("id")!!.toInt(),
                    total = doc.getLong("total") ?: 0L,
                    transactionType = TransactionType.valueOf(doc.getString("transactionType")!!),
                    description = doc.getString("description"),
                    timestamp = doc.getLong("timestamp") ?: 0L
                )
            } catch (e: Exception) {
                null
            }
        }

        if (transactions != null) {
            transactionDao.insertAll(transactions)
        }

    }

    suspend fun backupAll(userEmail: String?) {
        if (userEmail.isNullOrEmpty()) return

        val products = productDao.getAllProductsForBackup()
        if (products.isEmpty()) {
            // Mencegah backup jika tidak ada produk di lokal
            Toast.makeText(context, "Tidak ada data lokal untuk di-backup", Toast.LENGTH_SHORT).show()
            return
        }

        backupCategories(userEmail)
        backupProducts(userEmail)
    }

    suspend fun restoreAll(userEmail: String?) {
        if (userEmail.isNullOrEmpty()) return

        restoreCategories(userEmail)
        restoreProducts(userEmail)
        restoreTransaction(userEmail)
    }

}