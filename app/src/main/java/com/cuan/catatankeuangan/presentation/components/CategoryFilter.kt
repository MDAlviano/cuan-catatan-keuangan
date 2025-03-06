package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.viewmodel.FilterViewModel

@Composable
fun CategoryFilter(viewModel: FilterViewModel) {

    val selectedFilters by viewModel.selectedFilters.collectAsState()

//    TODO: implement data set dynamically
    val filters =
        listOf("Makanan", "Minuman", "Aksesoris", "Perkakas", "Pakaian", "Lainnya")

    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(filters) { filter ->
            FilterChip(
                selected = selectedFilters.contains(filter),
                onClick = { viewModel.toggleFilter(filter) },
                label = { Text(text = filter, fontFamily = ralewayFamily) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color1,
                    labelColor = Color1,
                    selectedLabelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = !selectedFilters.contains(filter),
                    selected = selectedFilters.contains(filter),
                    borderColor = Color1,
                )
            )
        }
    }
}