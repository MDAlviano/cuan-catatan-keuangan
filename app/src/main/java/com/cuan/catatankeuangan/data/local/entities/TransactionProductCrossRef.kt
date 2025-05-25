package com.cuan.catatankeuangan.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaction_product_cross_ref_table",
    foreignKeys = [
        ForeignKey(
            entity = Transaction::class,
            parentColumns = ["id"],
            childColumns = ["transactionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductSnapshot::class,
            parentColumns = ["id"],
            childColumns = ["snapshotId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("transactionId"), Index("snapshotId")]
)
data class TransactionProductCrossRef(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val transactionId: Int,
    val snapshotId: Int,
    val quantity: Int
)