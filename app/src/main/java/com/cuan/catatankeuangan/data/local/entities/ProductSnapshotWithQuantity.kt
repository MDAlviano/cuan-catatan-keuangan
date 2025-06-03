package com.cuan.catatankeuangan.data.local.entities

import androidx.room.Embedded

data class ProductSnapshotWithQuantity(
    @Embedded val snapshot: ProductSnapshot,
    val quantity: Int
)
