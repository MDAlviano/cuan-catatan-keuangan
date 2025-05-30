package com.cuan.catatankeuangan.repository

import android.net.Uri
import com.cuan.catatankeuangan.MainActivity
import com.cuan.catatankeuangan.data.local.dao.ProductDao
import com.cuan.catatankeuangan.data.local.entities.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await

class BackupManager(
    private val db: FirebaseFirestore,
    private val productDao: ProductDao,
    private val cloudinaryService: CloudinaryService,
    private val context: MainActivity
) {

    suspend fun backupProducts(userEmail: String?) {
        if (userEmail == null) return

        val products = productDao.getAllProductsForBackup()
        val userRef = db.collection("backups").document(userEmail)
        val productCollection = userRef.collection("products")

        // Clear old data
        val existing = productCollection.get().await()
        existing.documents.forEach { it.reference.delete() }

        for (product in products) {
            val finalImageUrl = if (product.imageUri != null && !product.imageUri!!.startsWith("http")) {
                suspendUploadImageToCloudinary(Uri.parse(product.imageUri), "product_${product.id}")
            } else product.imageUri

            val productMap = mapOf(
                "id" to product.id,
                "name" to product.name,
                "sellPrice" to product.sellPrice,
                "buyPrice" to product.buyPrice,
                "stock" to product.stock,
                "categoryId" to product.categoryId,
                "imageUri" to finalImageUrl
            )

            val docRef = productCollection.document(product.id.toString())
            docRef.set(productMap).await()
        }
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
                Product(
                    id = doc.getLong("id")!!.toInt(),
                    name = doc.getString("name") ?: "",
                    sellPrice = doc.getLong("sellPrice") ?: 0L,
                    buyPrice = doc.getLong("buyPrice") ?: 0L,
                    stock = doc.getLong("stock")?.toInt() ?: 0,
                    categoryId = doc.getLong("categoryId")?.toInt(),
                    imageUri = doc.getString("imageUri")
                )
            } catch (e: Exception) {
                null
            }
        }

        productDao.insertAll(products) // Room DAO method
    }

    private suspend fun suspendUploadImageToCloudinary(uri: Uri, publicId: String): String? =
        suspendCancellableCoroutine { cont ->
            cloudinaryService.uploadImage(
                uri = uri,
                publicId = publicId,
                onSuccess = { cont.resume(it, null) },
                onError = { cont.resume(null, null) }
            )
        }

}
