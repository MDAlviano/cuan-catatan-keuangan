package com.cuan.catatankeuangan.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType

class TransactionRepository(context: Context) {
    private val transactionDao = MainDatabase.getMainDatabase(context).transactionDao()

    fun getTotalPemasukan(): LiveData<Long> {
        return transactionDao.getTotalByType(TransactionType.MASUK)
    }

    fun getTotalPengeluaran(): LiveData<Long> {
        return transactionDao.getTotalByType(TransactionType.KELUAR)
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
}