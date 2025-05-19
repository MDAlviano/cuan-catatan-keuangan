package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun BalanceDetailsDialog(
    showBalanceDetails: Boolean,
    onDismiss: () -> Unit,
    text: String,
    balance: String? = null,
    income: String,
    expense: String
) {
    if (showBalanceDetails) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            Card(
                shape = RoundedCornerShape(10),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
//                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    if (balance !== null) {
                        Text(text = "Saldo: $balance", color = Color.Black)
                    }
                    Text(text = "Pemasukan: $income", color = Color.Black)
                    Text(text = "Pengeluaran: $expense", color = Color.Black)
                }
                TextButton(onClick = onDismiss) {
                    Text(text = "Tutup", color = Color.Black)
                }
            }
        }
    }
}

