package com.cuan.catatankeuangan.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product_snapshot_table")
data class ProductSnapshot(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val productId: Int,
    val sellPrice: Long,
    val buyPrice: Long,
    val timestamp: Long
) : Parcelable
