package com.cuan.catatankeuangan.presentation.screens.category

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cuan.catatankeuangan.data.local.entities.CategoryWithProducts
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
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
            containerColor = Color.White,
            shape = RoundedCornerShape(25f),
            text = {
                CustomTextField(
                    label = "Kategori Baru",
                    fieldValue = categoryName,
                    onValueChange = { categoryName = it },
                    hint = "Nama kategori"
                )
//                OutlinedTextField(
//                    value = categoryName,
//                    onValueChange = { categoryName = it },
//                    shape = RoundedCornerShape(25f),
//                    label = { Text("Nama kategori") }
//                )
            },
            confirmButton = {
                Button(
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
                    },
                    shape = RoundedCornerShape(25f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color1,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(24.dp, 0.dp)
                ) {
                    Text(text = "Simpan", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(25f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color3,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(24.dp, 0.dp)
                ) {
                    Text(text = "Batal", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun EditCategoryDialog(
    category: CategoryWithProducts,
    productViewModel: ProductViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {

    val context = LocalContext.current

    var categoryName by remember { mutableStateOf("") }

    LaunchedEffect(category) {
        categoryName = category.category.name
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Color.White,
            shape = RoundedCornerShape(25f),
            text = {
                CustomTextField(
                    label = "Ubah Nama",
                    fieldValue = categoryName,
                    onValueChange = { categoryName = it },
                    hint = "Nama kategori"
                )
//                OutlinedTextField(
//                    value = categoryName,
//                    onValueChange = { categoryName = it },
//                    shape = RoundedCornerShape(25f),
//                    label = { Text("Nama kategori") }
//                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (categoryName.isNotBlank()) {
                            productViewModel.renameCategory(
                                category = category,
                                newName = categoryName
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(
                                context,
                                "Harap isi kolom dengan benar.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        categoryName = ""
                    },
                    shape = RoundedCornerShape(25f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color1,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(24.dp, 0.dp)
                ) {
                    Text(text = "Simpan", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(25f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color3,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(24.dp, 0.dp)
                ) {
                    Text(text = "Batal", color = Color.White)
                }
            }
        )
    }
}


