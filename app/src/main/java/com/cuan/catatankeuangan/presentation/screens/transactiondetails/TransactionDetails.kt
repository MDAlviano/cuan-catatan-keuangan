package com.cuan.catatankeuangan.presentation.screens.transactiondetails

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.presentation.components.AutoResizedText
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.components.WarningDialog
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.theme.ralewayFamily
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import com.cuan.catatankeuangan.presentation.utils.getDate
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel

@Composable
fun TransactionDetails(
    transactionViewModel: TransactionViewModel,
    transaction: Transaction,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val transactionType = if (transaction.transactionType == TransactionType.INCOME) {
        "Pemasukan"
    } else if (transaction.transactionType == TransactionType.EXPENSE) {
        "Pengeluaran"
    } else {
        "-"
    }

    val description = transaction.description ?: "-"

    val information = mapOf(
        "Transaksi" to transactionType,
        "Jumlah Produk" to "2",
        "Total" to formatAsCurrency(transaction.total),
        "Keterangan" to description,
        "Waktu" to getDate(transaction.timestamp)
    )

    var showDeletePopup by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBgColor)
            ) {

                WarningDialog(
                    showDialog = showDeletePopup,
                    onDismiss = { showDeletePopup = false },
                    onConfirm = {
                        transactionViewModel.deleteTransaction(transaction)
                        showDeletePopup = false
                        onDismiss()
                        Toast.makeText(context, "Transaksi dihapus.", Toast.LENGTH_SHORT).show()
                    },
                    headerText = "Hapus Transaksi",
                    bodyText = "Apakah Anda yakin ingin menghapus transaksi ini?",
                    confirmText = "Hapus"
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TopBar(onClick = onDismiss, text = "Detail Transaksi")

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Text(text = "Informasi", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                        Column {
                            for ((index, entry) in information.entries.withIndex()) {
                                DetailItem(
                                    isProductData = false,
                                    key = entry.key,
                                    value = entry.value
                                )
                                if (index <= information.size - 2) {
                                    HorizontalDivider(thickness = (0.5).dp, color = Color.LightGray)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Text(
                            text = "Data Produk",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Column {
                            DetailItem(
                                isProductData = true,
                                key = "Sunglasses",
                                value = formatAsCurrency(10000),
                                quantity = 2
                            )
                            HorizontalDivider(thickness = (0.5).dp, color = Color.LightGray)
                            DetailItem(
                                isProductData = true,
                                key = "mbappe",
                                value = formatAsCurrency(8000),
                                quantity = 2
                            )
                            HorizontalDivider(thickness = (0.5).dp, color = Color.LightGray)
                            DetailItem(
                                isProductData = true,
                                key = "nig",
                                value = formatAsCurrency(1000),
                                quantity = 2
                            )
                        }
                    }

                    if (transaction.transactionType == TransactionType.EXPENSE) {

                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { showDeletePopup = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color3
                        ),
                        contentPadding = PaddingValues(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp, vertical = 32.dp)
                    ) {
                        Text(
                            text = "Hapus",
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

@Composable
fun DetailItem(isProductData: Boolean, key: String, value: String, quantity: Int? = null) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 14.dp)
    ) {
        Text(
            text = key,
            fontSize = 16.sp,
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        AutoResizedText(
            text = value,
            fontSize = 16.sp,
            style = TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End,
            ),
            modifier = Modifier.weight(1f)
        )
        if (isProductData) {
            Text(
                text = "$quantity",
                fontFamily = interFamily,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(Color1, RoundedCornerShape(25f))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}