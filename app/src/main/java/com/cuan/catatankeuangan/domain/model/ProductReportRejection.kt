package com.cuan.catatankeuangan.domain.model

import java.time.LocalDate

data class ProductReportRejection(
    val productId: Int,
    val productName: String,
    val productImageUri: String?,
    val sellPrice: Long,
    val buyPrice: Long,
    val totalSold: Int,
    val totalRevenue: Long,
    val totalCost: Long,
    val totalProfit: Long,
    val categoryId: Int,
    val categoryName: String?,
    val transactionDate: LocalDate
)