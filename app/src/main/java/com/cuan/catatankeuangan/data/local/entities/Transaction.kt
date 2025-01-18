package com.cuan.catatankeuangan.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val totalAmount: Long,
//    val transactionType: TransactionType,
    val description: String?,
    val timestamp: Long,
    val productDetails: String?
) : Parcelable