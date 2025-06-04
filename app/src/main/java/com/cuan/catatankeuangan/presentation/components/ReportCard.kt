package com.cuan.catatankeuangan.presentation.components

//import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.domain.model.CategoryReport
import com.cuan.catatankeuangan.domain.model.ProductReportRejection
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReportCategoryCard(categoryReport: CategoryReport, modifier: Modifier = Modifier) {
    val sdf = SimpleDateFormat("HH.mm", Locale("id", "ID"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp).then(modifier),
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
                    painter = painterResource(R.drawable.two_way_arrow),
                    contentDescription = "icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = categoryReport.categoryName.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        fontFamily = interFamily,
                        text = "${categoryReport.totalTransactions} Transaksi",
//                        fontFamily = outfitFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = formatAsCurrency(categoryReport.totalIncome.toLong()),
//                    fontFamily = outfitFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = formatAsCurrency(categoryReport.totalExpense.toLong()),
//                    fontFamily = outfitFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color3
                )
            }
        }
    }
}

@Composable
fun ReportCard(
    productReportRejection: ProductReportRejection,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clickable{ onClick() }
        ) {
            Box(
                modifier = Modifier
                    .heightIn(0.dp, 150.dp)
                    .widthIn(0.dp, 150.dp)
                    .fillMaxSize()
            ) {
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
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10))
                )
                Text(
                    text = "${productReportRejection.totalSold} Terjual",
                    fontSize = 12.sp,
//                    fontFamily = outfitFamily,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp, 6.dp)
                        .clip(RoundedCornerShape(size = 12.dp))
                        .background(Color1)
                        .padding(6.dp, 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp, 4.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = productReportRejection.productName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = productReportRejection.categoryName ?: "-",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 12.sp,
                    color = Color1
                )
                Text(
                    text = formatAsCurrency(productReportRejection.sellPrice),
                    fontSize = 18.sp,
//                        fontFamily = outfitFamily,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 34.sp
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(width = 1.dp, color = Color1, shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Keuntungan",
                            fontSize = 10.sp,
                            color = Color1
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                            .background(Color1)
                            .border(width = 1.dp, color = Color1, shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                            text = formatAsCurrency(productReportRejection.totalProfit),
                            textAlign = TextAlign.End,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}