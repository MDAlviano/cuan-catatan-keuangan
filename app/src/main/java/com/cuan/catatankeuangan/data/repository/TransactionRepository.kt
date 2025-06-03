package com.cuan.catatankeuangan.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.ProductSnapshot
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionProductCrossRef
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.domain.model.SelectedProduct

class TransactionRepository(context: Context) {
    private val transactionDao = MainDatabase.getMainDatabase(context).transactionDao()

    fun getTotalPemasukan(): LiveData<Long> {
        return transactionDao.getTotalByType(TransactionType.INCOME)
    }

    fun getTotalPengeluaran(): LiveData<Long> {
        return transactionDao.getTotalByType(TransactionType.EXPENSE)
    }

    fun getTodayPemasukan(): LiveData<Long> {
        return transactionDao.getTodayPemasukan()
    }

    fun getTodayPengeluaran(): LiveData<Long> {
        return transactionDao.getTodayPengeluaran()
    }

    fun getTodayTransactions(): LiveData<List<Transaction>> {
        return transactionDao.getTodayTransactions()
    }

    suspend fun saveSelectedProducts(
        transactionId: Int,
        selectedProducts: List<SelectedProduct>
    ) {
        selectedProducts.forEach { selected ->
            // Simpan snapshot
            val snapshot = ProductSnapshot(
                id = 0,
                productId = selected.product.id,
                sellPrice = selected.product.sellPrice,
                buyPrice = selected.product.buyPrice,
                timestamp = System.currentTimeMillis()
            )
            val snapshotId = transactionDao.addProductSnapshot(snapshot).toInt()

            // Simpan cross-ref
            val crossRef = TransactionProductCrossRef(
                id = 0,
                transactionId = transactionId,
                snapshotId = snapshotId,
                quantity = selected.quantity
            )
            transactionDao.addTransactCrossRef(crossRef)
        }
    }
}