package com.cuan.catatankeuangan.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilterViewModel : ViewModel() {
    private val _selectedFilter = MutableStateFlow<String?>(null)
    val selectedFilter = _selectedFilter.asStateFlow()

    fun selectFilter(filter: String) {
        _selectedFilter.value = if (_selectedFilter.value == filter) null else filter
    }
}