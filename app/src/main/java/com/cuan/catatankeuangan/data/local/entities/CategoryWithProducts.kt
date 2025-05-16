package com.cuan.catatankeuangan.data.local.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

data class CategoryWithProducts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<Product>
)