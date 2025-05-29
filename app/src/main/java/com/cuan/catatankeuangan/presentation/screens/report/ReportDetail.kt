package com.cuan.catatankeuangan.presentation.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency

@Composable
fun ReportDetail(
    product: Product,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val detailProduct = mapOf(
        "Produk" to product.name,
        "Harga" to formatAsCurrency(product.sellPrice),
        "Produk Terjual" to "15 Unit",
        "Total Penjualan" to "Rp50.000",
        "Total Modal" to "Rp30.000",
        "Waktu" to "Sen, 25/02/25",
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopBar(onClick = onDismiss, text = "Kategori")

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = if (product.imageUri == null) {
                            painterResource(id = R.drawable.profile)
                        } else {
                            rememberAsyncImagePainter(
                                model = product.imageUri
                            )
                        },
                        contentDescription = "Product image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .clip(RoundedCornerShape(10)),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column {
                        for ((index, content) in detailProduct.entries.withIndex()) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = content.key,
                                    textAlign = TextAlign.Start
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = content.value,
                                    textAlign = TextAlign.End
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            if (index <= detailProduct.size - 2) {
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = Color3
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color1)
                                .clip(RoundedCornerShape(size = 10.dp))
                                .padding(all = 12.dp)
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Keuntungan",
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = formatAsCurrency(product.sellPrice - product.buyPrice),
                                textAlign = TextAlign.End,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }

                    }

                }
            }
        }
    }
}
