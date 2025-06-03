package com.cuan.catatankeuangan.presentation.screens.newtransaction

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.domain.model.SelectedProduct
import com.cuan.catatankeuangan.presentation.components.CurrencyTextField
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.components.SelectedTransactionProductCard
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.components.TransactionProductCard
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.viewmodel.ProductViewModel
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTransactionDialog(
    transactionViewModel: TransactionViewModel,
    productViewModel: ProductViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val context = LocalContext.current
    val currentTime = System.currentTimeMillis()

    val categoryList by productViewModel.allCategories.observeAsState(initial = emptyList())

    var selectedType by remember { mutableStateOf("Pemasukan") }
    var totalAmountField by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf("") }

//    val selectedProducts = remember { mutableStateListOf<Product>() }

    val selectedProducts = transactionViewModel.selectedProducts

    val rawTotalAmount = remember { mutableStateOf("") }

    var showProductSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

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
                if (showProductSheet) {
                    SelectProductSheet(
                        sheetState = sheetState,
                        onDismiss = { showProductSheet = false },
                        productViewModel = productViewModel,
                        transactionViewModel = transactionViewModel,
                        onProductSelected = { product ->
                            showProductSheet = false
                        }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TopBar(onClick = onDismiss, text = "Transaksi Baru")

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {

                        AssistChip(
                            onClick = {
                                selectedType = "Pemasukan"
                                totalAmountField = TextFieldValue("")
                                description = ""
                                rawTotalAmount.value = ""
                            },
                            label = {
                                Text(
                                    text = "Pemasukan",
                                    fontFamily = ralewayFamily,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_up),
                                    contentDescription = "Pemasukan",
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .size(32.dp)
                                        .background(
                                            if (selectedType == "Pemasukan") {
                                                Color.White
                                            } else {
                                                Color1
                                            },
                                            shape = RoundedCornerShape(100)
                                        )
                                        .padding(8.dp)
                                        .rotate(180F)
                                )
                            },
                            shape = RoundedCornerShape(100),
                            border = BorderStroke(1.dp, Color1),
                            colors = if (selectedType == "Pemasukan") {
                                AssistChipDefaults.assistChipColors(
                                    containerColor = Color1,
                                    labelColor = Color.White,
                                    leadingIconContentColor = Color1
                                )
                            } else {
                                AssistChipDefaults.assistChipColors(
                                    containerColor = Color.Unspecified,
                                    labelColor = Color1,
                                    leadingIconContentColor = Color.White
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                        )

                        AssistChip(
                            onClick = {
                                selectedType = "Pengeluaran"
                                totalAmountField = TextFieldValue("")
                                description = ""
                                rawTotalAmount.value = ""
                            },
                            label = {
                                Text(
                                    text = "Pengeluaran",
                                    fontFamily = ralewayFamily,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_up),
                                    contentDescription = "Pengeluaran",
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .size(32.dp)
                                        .background(
                                            if (selectedType == "Pengeluaran") {
                                                Color.White
                                            } else {
                                                Color3
                                            },
                                            shape = RoundedCornerShape(100)
                                        )
                                        .padding(8.dp)
                                )
                            },
                            shape = RoundedCornerShape(100),
                            border = BorderStroke(1.dp, Color3),
                            colors = if (selectedType == "Pengeluaran") {
                                AssistChipDefaults.assistChipColors(
                                    containerColor = Color3,
                                    labelColor = Color.White,
                                    leadingIconContentColor = Color3
                                )
                            } else {
                                AssistChipDefaults.assistChipColors(
                                    containerColor = Color.Unspecified,
                                    labelColor = Color3,
                                    leadingIconContentColor = Color.White
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        CurrencyTextField(
                            label = if (selectedType == "Pemasukan") "Total Pemasukan" else "Total Pengeluaran",
                            fieldValue = totalAmountField,
                            rawValue = rawTotalAmount,
                            onValueChange = { totalAmountField = it }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        CustomTextField(
                            label = "Keterangan",
                            fieldValue = description,
                            onValueChange = {
                                description = it
                            },
                            hint = "Deskripsi"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (selectedType == "Pemasukan") {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { showProductSheet = true },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color1),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.rounded_add),
                                            contentDescription = "Add product",
                                            tint = Color1,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .background(
                                                    Color.White,
                                                    RoundedCornerShape(100)
                                                )
                                        )
                                        Text(
                                            text = "Tambah Produk",
                                            fontFamily = ralewayFamily,
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                                Button(
                                    onClick = {
                                        transactionViewModel.clearSelectedProducts()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, Color3),
                                    contentPadding = PaddingValues(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color3
                                    ),
                                    modifier = Modifier
                                ) {
                                    Icon(
                                        Icons.Outlined.Delete,
                                        contentDescription = "Delete",
//                                    tint = Color3,
                                        Modifier
                                            .size(32.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // ini adalah list produk yang sudah ditambahkan
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.height(240.dp)
                            ) {
                                items(selectedProducts) { product ->
                                    SelectedTransactionProductCard(
                                        product = product.product,
                                        quantity = product.quantity
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // button untuk menyimpan transaksi
                        Button(
                            onClick = {
                                val totalAmount = rawTotalAmount.value.toLongOrNull() ?: 0L
                                if (totalAmount == 0L) {
                                    Toast.makeText(
                                        context,
                                        "Harap isi kolom dengan angka yang valid.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // Add transaction to database
                                    val transaction = Transaction(
                                        id = 0,
                                        description = description.ifEmpty { null },
                                        transactionType = if (selectedType == "Pemasukan") {
                                            TransactionType.INCOME
                                        } else {
                                            TransactionType.EXPENSE
                                        },
                                        total = rawTotalAmount.value.toLongOrNull() ?: 0L,
                                        timestamp = currentTime
                                    )
                                    transactionViewModel.saveTransactionAndProducts(transaction) {
                                        rawTotalAmount.value = ""
                                        totalAmountField = TextFieldValue("")
                                        description = ""
                                        transactionViewModel.clearSelectedProducts()
                                        onConfirm()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color1
                            ),
                            contentPadding = PaddingValues(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 60.dp, vertical = 32.dp)
                        ) {
                            Text(
                                text = "Simpan",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = ralewayFamily,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}

