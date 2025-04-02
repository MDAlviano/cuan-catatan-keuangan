package com.cuan.catatankeuangan.presentation.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.MainBgColor

@Composable
fun ProductCategory(showDialog: Boolean, onDismiss: () -> Unit) {

    val categories = listOf("Makanan", "Minuman", "Aksesoris", "Perkakas", "Pakaian", "Lainnya")

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBgColor)
            ) {
                Column {
                    TopBar(onClick = onDismiss, text = "Kategori")

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn {
                        items(categories) { category ->
                            CategoryListItem(category)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CategoryListItem(category: String) {
    Card() {
        Text(text = category)
    }
}
