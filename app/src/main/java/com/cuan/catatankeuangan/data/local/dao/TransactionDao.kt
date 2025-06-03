package com.cuan.catatankeuangan.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.ProductSnapshot
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionProductCrossRef
import com.cuan.catatankeuangan.data.local.entities.TransactionType

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction): Long

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Query("SELECT COALESCE(SUM(total), 0) FROM transaction_table WHERE transactionType = :type")
    fun getTotalByType(type: TransactionType): LiveData<Long>

    @Query(
        """
    SELECT * FROM transaction_table
    WHERE date(timestamp / 1000, 'unixepoch', 'localtime') = date('now', 'localtime')
    ORDER BY timestamp DESC 
    """
    )
    fun getTodayTransactions(): LiveData<List<Transaction>>

    @Query("SELECT IFNULL(SUM(total), 0) FROM transaction_table WHERE transactionType = 'INCOME' AND date(timestamp / 1000, 'unixepoch', 'localtime') = date('now', 'localtime')")
    fun getTodayPemasukan(): LiveData<Long>

    @Query("SELECT IFNULL(SUM(total), 0) FROM transaction_table WHERE transactionType = 'EXPENSE' AND date(timestamp / 1000, 'unixepoch', 'localtime') = date('now', 'localtime')")
    fun getTodayPengeluaran(): LiveData<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProductSnapshot(snapshot: ProductSnapshot): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransactCrossRef(refs: TransactionProductCrossRef)

}