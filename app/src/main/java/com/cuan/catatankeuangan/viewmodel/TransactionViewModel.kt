package com.cuan.catatankeuangan.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.repository.TransactionRepository
import com.cuan.catatankeuangan.domain.model.SelectedProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(application: Application): AndroidViewModel(application) {
    private val transactionDao = MainDatabase.getMainDatabase(application).transactionDao()
    private val transactionRepository: TransactionRepository = TransactionRepository(application)

    private val _selectedProducts = mutableStateListOf<SelectedProduct>()
    private val _refreshTrigger = mutableStateOf(Unit)

    val allTransaction: LiveData<List<Transaction>> = transactionDao.getAllTransaction()
    val todayTransactions: LiveData<List<Transaction>> = transactionDao.getTodayTransactions()

    val selectedProducts: List<SelectedProduct> get() = _selectedProducts
    val refreshTrigger: State<Unit> get() = _refreshTrigger

    fun triggerRefresh() {
        _refreshTrigger.value = Unit
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionDao.addTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionDao.deleteTransaction(transaction)
        }
    }

    fun addOrIncrement(product: Product) {
        val existing = _selectedProducts.find { it.product.id == product.id }
        if (existing != null) {
            if (existing.quantity < product.stock) {
                val index = _selectedProducts.indexOf(existing)
                _selectedProducts[index] = existing.copy(quantity = existing.quantity + 1)
            }
        } else {
            _selectedProducts.add(SelectedProduct(product, 1))
        }
    }

    fun decrement(product: Product) {
        val existing = _selectedProducts.find { it.product.id == product.id }
        if (existing != null) {
            if (existing.quantity > 1) {
                val index = _selectedProducts.indexOf(existing)
                _selectedProducts[index] = existing.copy(quantity = existing.quantity - 1)
            } else {
                _selectedProducts.remove(existing)
            }
        }
    }

    fun removeProduct(productId: Int) {
        _selectedProducts.removeAll { it.product.id == productId }
    }

    fun clearSelectedProducts() {
        _selectedProducts.clear()
    }

    fun saveProductSnapshotAndCrossRefs(transactionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.saveSelectedProducts(transactionId, selectedProducts)
        }
    }

    fun saveTransactionAndProducts(transaction: Transaction, onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val transactionId = transactionRepository.addTransaksi(transaction).toString().toInt() // java.lang.NumberFormatException: For input string: "kotlin.Unit"
            saveProductSnapshotAndCrossRefs(transactionId)

            withContext(Dispatchers.Main) {
                clearSelectedProducts()
                onDone()
            }
        }
    }

    val totalPemasukan: LiveData<Long> = transactionRepository.getTotalPemasukan()
    val totalPengeluaran: LiveData<Long> = transactionRepository.getTotalPengeluaran()

    val todayPemasukan: LiveData<Long> = transactionRepository.getTodayPemasukan()
    val todayPengeluaran: LiveData<Long> = transactionRepository.getTodayPengeluaran()

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