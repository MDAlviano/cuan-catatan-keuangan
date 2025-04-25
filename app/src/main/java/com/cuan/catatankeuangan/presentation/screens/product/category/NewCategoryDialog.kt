package com.cuan.catatankeuangan.presentation.screens.product.category

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.cuan.catatankeuangan.viewmodel.ProductViewModel

@Composable
fun NewCategoryDialog(
    productViewModel: ProductViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {

    val context = LocalContext.current

    var categoryName by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Kategori Baru") },
            text = {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Nama kategori") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (categoryName.isNotBlank()) {
                            productViewModel.addCategory(categoryName)
                            onDismiss()
                        } else {
                            Toast.makeText(
                                context,
                                "Harap isi kolom dengan benar.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        categoryName = ""
                    }
                ) {
                    Text("Tambah")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Batal")
                }
            }
        )
    }
}
