package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DeleteProductDialog(
    showDeletePopup: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDeletePopup) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                shape = RoundedCornerShape(10),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(36.dp, 24.dp)
                ) {
                    Text(text = "Hapus produk ini?")

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { /*TODO*/ onDismiss() }) {
                            Text(text = "Batal")
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(onClick = { /*TODO*/ onConfirm() }) {
                            Text(text = "Hapus")
                        }
                    }
                }
            }
        }
    }

}