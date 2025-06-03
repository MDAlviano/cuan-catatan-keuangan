package com.cuan.catatankeuangan.domain.model

data class CategoryReport(
    val categoryId: Int,
    val categoryName: String,
    val totalTransactions: Int,
    val totalIncome: Int,
    val totalExpense: Int,
    val latestTransactionTime: Long?
)
