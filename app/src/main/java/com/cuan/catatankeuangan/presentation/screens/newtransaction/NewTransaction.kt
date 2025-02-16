package com.cuan.catatankeuangan.presentation.screens.newtransaction

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.presentation.utils.formatNominal
import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel

@Composable
fun NewTransactionDialog(
    transactionViewModel: TransactionViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 260.dp)
                    .height(IntrinsicSize.Max)
            ) {
                NewTransactionForm(
                    transactionViewModel = transactionViewModel,
                    onConfirm,
                    onDismiss
                )
            }
        }
    }
}

@Composable
fun NewTransactionForm(
    transactionViewModel: TransactionViewModel,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val currentTime = System.currentTimeMillis()

    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    var rawTotalAmount by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf("Pemasukan") }

    Column(modifier = Modifier.padding(24.dp, 12.dp)) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val rawInput = newValue.text.filter { it.isDigit() }
                rawTotalAmount = rawInput

                val formattedText =
                    if (rawInput.isNotEmpty()) formatNominal(rawInput.toLong()) else ""

                val cursorOffset =
                    newValue.selection.start + (formattedText.length - newValue.text.length)

                textFieldValue = newValue.copy(
                    text = formattedText,
                    selection = TextRange(cursorOffset.coerceIn(0, formattedText.length))
                )
            },
            label = { Text(text = "Harga") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontFamily = outfitFamily),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            transactionType = if (transactionType == "Pemasukan") "Pengeluaran" else "Pemasukan"
        }) {
            Text(text = "Jenis transaksi: $transactionType")
        }
        Button(
            onClick = {
                val totalAmount = rawTotalAmount.toLongOrNull() ?: 0L
                if (totalAmount == 0L) {
                    Toast.makeText(
                        context,
                        "Harap isi kolom dengan angka yang valid.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val transaction = Transaction(
                        id = 0, deskripsi = null,
                        tipeTransaksi = if (transactionType == "Pemasukan") {
                            TransactionType.MASUK
                        } else {
                            TransactionType.KELUAR
                        },
                        total = rawTotalAmount.toLongOrNull() ?: 0L,
                        timestamp = currentTime
                    )
                    transactionViewModel.addTransaction(transaction)
                    rawTotalAmount = ""
                    textFieldValue = TextFieldValue("")

                    onConfirm()
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tambahkan transaksi")
        }
        TextButton(onClick = onDismiss) {
            Text(text = "Batal")
        }
    }
}