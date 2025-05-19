package com.cuan.catatankeuangan.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "product_table",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val buyPrice: Long,
    val sellPrice: Long,
    val stock: Int,
    val categoryId: Int? = null,
    @ColumnInfo(name = "image_path")
    val imageUri: String? = null,
//    val bookId: String
) : Parcelable