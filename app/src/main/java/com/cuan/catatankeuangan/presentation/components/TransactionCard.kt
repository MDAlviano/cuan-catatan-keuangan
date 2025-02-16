package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color5
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionCard(transaction: Transaction) {
    val sdf = SimpleDateFormat("HH.mm", Locale("id", "ID"))

    val pemasukanColor = Color1
    val pengeluaranColor = Color5

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_transaction),
                    contentDescription = "Transaksi",
                    tint = if (transaction.tipeTransaksi == TransactionType.MASUK) {
                        pemasukanColor
                    } else {
                        pengeluaranColor
                    },
                    modifier = if (transaction.tipeTransaksi == TransactionType.MASUK) {
                        Modifier.rotate(0F)
                    } else {
                        Modifier.rotate(180F)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = if (transaction.tipeTransaksi == TransactionType.MASUK) {
                            "Pemasukan"
                        } else {
                            "Pengeluaran"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = sdf.format(Date(transaction.timestamp)),
                        fontFamily = outfitFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
            Text(
                text = formatAsCurrency(transaction.total),
                fontFamily = outfitFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = if (transaction.tipeTransaksi == TransactionType.MASUK) {
                    pemasukanColor
                } else {
                    pengeluaranColor
                }
            )
        }
    }
}