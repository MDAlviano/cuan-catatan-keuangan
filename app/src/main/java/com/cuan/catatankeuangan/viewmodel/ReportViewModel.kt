package com.cuan.catatankeuangan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cuan.catatankeuangan.data.repository.ReportRepository
import com.cuan.catatankeuangan.domain.model.CategoryReport
import com.cuan.catatankeuangan.domain.model.ProductReportRejection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class ReportViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ReportRepository = ReportRepository(application)

    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    private val _startDate = MutableStateFlow<LocalDate?>(null)
    private val _endDate = MutableStateFlow<LocalDate?>(null)
    private val _productName = MutableStateFlow<String?>(null)
    private val _categoryName = MutableStateFlow<String?>(null)

    fun setCategoryFilter(id: Int?) {
        _selectedCategoryId.value = id
    }
    fun setSingleDateFilter(date: LocalDate?) {
        _selectedDate.value = date
    }
    fun setDateRange(startDate: LocalDate?, endDate: LocalDate?) {
        _startDate.value = startDate
        _endDate.value = endDate
    }
    fun setProductName(name: String?) {
        _productName.value = name
    }
    fun setCategoryName(name: String?) {
        _categoryName.value = name
    }

    val salesReport: StateFlow<List<ProductReportRejection>> =
        combine(
            repository.getSalesReport(),
            _selectedCategoryId,
            _selectedDate,
            _startDate,
            _endDate,
            _productName
        ) { flowsArray ->
            @Suppress("UNCHECKED_CAST")
            val reports = flowsArray[0] as List<ProductReportRejection>
            val categoryId = flowsArray[1] as Int?
            val selectedDate = flowsArray[2] as LocalDate?
            val startDate = flowsArray[3] as LocalDate?
            val endDate = flowsArray[4] as LocalDate?
            val productName = flowsArray[5] as String?

            reports.filter { report ->
                val matchesCategory = categoryId == null || report.categoryId == categoryId
                val matchesDate = when {
                    selectedDate != null -> report.transactionDate == selectedDate
                    startDate != null && endDate != null -> report.transactionDate in startDate..endDate
                    else -> true
                }
                val matchesName = productName.isNullOrBlank() || report.productName.contains(productName, ignoreCase = true)
                matchesCategory && matchesDate && matchesName
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categoryReport: StateFlow<List<CategoryReport>> =
        combine(
            repository.getCategoryReport(),
            _selectedDate,
            _startDate,
            _endDate,
            _categoryName
        ) { reports, selectedDate, startDate, endDate, categoryName ->

            reports.filter { report ->
                val reportDate = report.latestTransactionTime?.toLocalDate()

                val matchesDate = when {
                    selectedDate != null -> reportDate == selectedDate
                    startDate != null && endDate != null -> reportDate!! in startDate..endDate
                    else -> true
                }

                val matchesName = categoryName.isNullOrBlank() ||
                        report.categoryName.contains(categoryName, ignoreCase = true)

                matchesDate && matchesName
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalIncome: StateFlow<Int> = categoryReport.map { list ->
        list.sumOf { it.totalIncome }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalExpense: StateFlow<Int> = categoryReport.map { list ->
        list.sumOf { it.totalExpense }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private fun Long.toLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    }

}