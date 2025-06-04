package com.cuan.catatankeuangan.domain.model

import com.cuan.catatankeuangan.data.local.entities.Product

data class SelectedProduct(
    val product: Product,
    var quantity: Int = 1
)
