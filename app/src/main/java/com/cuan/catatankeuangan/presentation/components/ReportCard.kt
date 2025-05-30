package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Belum final, sebaiknya jangan dipakai terlebih dahulu kecuali untuk uji coba
 *
 * @author mdalviano
 */
@Composable
fun ReportCategoryCard(category: Category, transaction: Transaction, modifier: Modifier = Modifier) {
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
                        text = "Upah",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "10 Transaksi",
                        fontFamily = interFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Rp5.500",
                    fontFamily = interFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Rp500",
                    fontFamily = interFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color3
                )
            }
        }
    }
}

/**
 * Belum final, sebaiknya jangan dipakai terlebih dahulu kecuali untuk uji coba
 *
 * @author mdalviano
 */
@Composable
fun ReportCard(
    transaction: Transaction,
    product: Product,
    category: String? = null,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = if (product.stock == 0) {
            Modifier
        } else {
            Modifier
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .heightIn(0.dp, 150.dp)
                    .widthIn(0.dp, 150.dp)
                    .fillMaxSize()
            ) {
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
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10))
                )
                Text(
                    text = "Stok: ${product.stock}",
                    fontSize = 12.sp,
                    fontFamily = interFamily,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp, 6.dp)
                        .background(
                            if (product.stock == 0) Color3 else Color1,
                            RoundedCornerShape(50)
                        )
                        .padding(6.dp, 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp, 4.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = category ?: "-",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 12.sp,
                    color = Color1
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = formatAsCurrency(product.sellPrice),
                        fontSize = 18.sp,
                        fontFamily = interFamily,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 34.sp
                    )
                }
            }
        }
    }
}