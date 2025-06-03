package com.cuan.catatankeuangan.data.repository

import android.content.Context
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.domain.model.CategoryReport
import com.cuan.catatankeuangan.domain.model.ProductReportRejection
import kotlinx.coroutines.flow.Flow

class ReportRepository(context: Context) {
    private val reportDao = MainDatabase.getMainDatabase(context).reportDao()

    fun getSalesReport(): Flow<List<ProductReportRejection>> = reportDao.getSalesReport()

//    fun getCategoryReport(): Flow<List<CategoryReport>> = reportDao.getCategoryReport()
}