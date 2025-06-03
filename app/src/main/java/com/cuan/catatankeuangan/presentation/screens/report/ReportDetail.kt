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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.domain.model.ProductReportRejection
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency

@Composable
fun ReportDetail(
    productReportRejection: ProductReportRejection,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val detailProduct = mapOf(
        "Produk" to productReportRejection.productName,
        "Harga" to formatAsCurrency(productReportRejection.sellPrice),
        "Produk Terjual" to productReportRejection.totalSold.toString(),
        "Total Penjualan" to formatAsCurrency(productReportRejection.totalSold.times(productReportRejection.sellPrice)),
        "Total Modal" to formatAsCurrency(productReportRejection.totalSold.times(productReportRejection.buyPrice)),
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
                        painter = if (productReportRejection.productImageUri == null) {
                            painterResource(id = R.drawable.profile)
                        } else {
                            rememberAsyncImagePainter(
                                model = productReportRejection.productImageUri
                            )
                        },
                        contentDescription = "Product image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(120.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(10)),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        for ((index, content) in detailProduct.entries.withIndex()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 14.dp)
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

                            if (index <= detailProduct.size - 2) {
                                HorizontalDivider(thickness = (0.5).dp, color = Color.LightGray)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(size = 14.dp))
                                .background(Color1)
                                .padding(horizontal = 8.dp, vertical = 14.dp)
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Keuntungan",
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = formatAsCurrency(productReportRejection.totalProfit),
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
