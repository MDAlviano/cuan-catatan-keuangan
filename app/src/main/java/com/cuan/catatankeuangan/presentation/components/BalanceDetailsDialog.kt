package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.interFamily

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
                shape = RoundedCornerShape(25f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
//                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = text,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .background(Color2, RoundedCornerShape(15))
                            .padding(14.dp, 4.dp),
                        color = Color.White
                    )

                    Column() {
                        if (balance !== null) {
                            Text(
                                text = "Saldo: $balance",
                                fontFamily = interFamily,
                                color = Color.Black
                            )
                        } else {
                            Text(
                                text = "-",
                                fontFamily = interFamily,
                                color = Color.Black
                            )
                        }
                        Text(
                            text = "Pemasukan: $income",
                            fontFamily = interFamily,
                            color = Color.Black
                        )
                        Text(
                            text = "Pengeluaran: $expense",
                            fontFamily = interFamily,
                            color = Color.Black
                        )
                    }
                }
                TextButton(onClick = onDismiss) {
                    Text(text = "Tutup", color = Color.Black)
                }
            }
        }
    }
}

