package com.cuan.catatankeuangan.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilterViewModel : ViewModel() {
    private val _selectedFilters = MutableStateFlow<Set<String>>(emptySet())
    val selectedFilters = _selectedFilters.asStateFlow()

    fun toggleFilter(filter: String) {
        _selectedFilters.value = _selectedFilters.value.toMutableSet().apply {
            if (contains(filter)) remove(filter) else add(filter)
        }
    }
}