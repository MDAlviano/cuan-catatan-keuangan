package com.cuan.catatankeuangan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionDao = MainDatabase.getMainDatabase(application).transactionDao()
    private val transactionRepository: TransactionRepository = TransactionRepository(application)

    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransaction()

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionDao.addTransaction(transaction)
        }
    }

    val totalPemasukan: LiveData<Long> = transactionRepository.getTotalPemasukan()

    val totalPengeluaran: LiveData<Long> = transactionRepository.getTotalPengeluaran()

    val totalSaldo: LiveData<Long> = MediatorLiveData<Long>().apply {
        var pemasukan = 0L
        var pengeluaran = 0L

        addSource(totalPemasukan) { pemasukanValue ->
            pemasukan = pemasukanValue ?: 0L
            value = pemasukan - pengeluaran
        }

        addSource(totalPengeluaran) { pengeluaranValue ->
            pengeluaran = pengeluaranValue ?: 0L
            value = pemasukan - pengeluaran
        }
    }

}