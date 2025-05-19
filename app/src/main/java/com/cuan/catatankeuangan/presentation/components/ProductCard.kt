package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import coil.compose.rememberAsyncImagePainter
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency

@Composable
fun ProductCard(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    category: String
) {

    var actionExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = if (product.stock == 0) {
            Modifier
        } else {
            Modifier
        },
//            .fillMaxHeight()
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
                        painterResource(id = R.drawable.no_image)
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
                    text = category,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 12.sp,
                    color = Color1
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    AutoResizedText(
                        text = formatAsCurrency(product.sellPrice),
                        fontSize = 18.sp,
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 34.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    // Product card dropdown menu
                    Box(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        IconButton(
                            onClick = { actionExpanded = !actionExpanded },
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        ) {
                            Icon(Icons.Default.MoreVert, "Product options", tint = Color.Black)
                        }
                        DropdownMenu(
                            expanded = actionExpanded,
                            onDismissRequest = { actionExpanded = false },
                            modifier = Modifier.background(Color.White),
                            properties = PopupProperties()
                        ) {

                            DropdownMenuItem(
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Edit product",
                                        modifier = Modifier.width(18.dp)
                                    )
                                },
                                text = { Text(text = "Edit") },
                                colors = MenuDefaults.itemColors(
                                    trailingIconColor = Color.Black
                                ),
                                onClick = {
                                    actionExpanded = false
                                    onEditClick()
                                },
                            )

                            DropdownMenuItem(
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.Refresh,
                                        contentDescription = "Restock product",
                                        modifier = Modifier.width(18.dp)
                                    )
                                },
                                text = { Text(text = "Restok") },
                                colors = MenuDefaults.itemColors(
                                    trailingIconColor = Color.Black
                                ),
                                onClick = { /*TODO*/ },
                            )

                            DropdownMenuItem(
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.trash),
                                        contentDescription = "Delete product",
                                        modifier = Modifier.width(18.dp)
                                    )
                                },
                                text = { Text(text = "Hapus", color = Color3) },
                                colors = MenuDefaults.itemColors(
                                    trailingIconColor = Color3
                                ),
                                onClick = {
                                    actionExpanded = false
                                    onDeleteClick()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionProductCard(
    product: Product,
    category: String,
    count: Int = 0,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
    ) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Image(
                    painter = if (product.imageUri == null) {
                        painterResource(id = R.drawable.no_image)
                    } else {
                        rememberAsyncImagePainter(
                            model = product.imageUri
                        )
                    },
                    contentDescription = "Product image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10))
                )
                Text(
                    text = "Stok: ${product.stock}",
                    fontSize = 10.sp,
                    fontFamily = interFamily,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp, 6.dp)
                        .background(Color1, RoundedCornerShape(50))
                        .padding(4.dp, 0.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(0.dp, 120.dp)
                    .padding(4.dp, 4.dp, 4.dp, 4.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
                        lineHeight = 34.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
    ) {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Image(
                    painter = if (product.imageUri == null) {
                        painterResource(id = R.drawable.no_image)
                    } else {
                        rememberAsyncImagePainter(
                            model = product.imageUri
                        )
                    },
                    contentDescription = "Product image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10))
                )
                Text(
                    text = "Stok: ${product.stock}",
                    fontSize = 10.sp,
                    fontFamily = interFamily,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp, 6.dp)
                        .background(Color1, RoundedCornerShape(50))
                        .padding(4.dp, 0.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(0.dp, 120.dp)
                    .padding(4.dp, 4.dp, 4.dp, 4.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AutoResizedText(
                        text = formatAsCurrency(product.sellPrice),
                        fontSize = 16.sp,
                        style = TextStyle(
                            fontFamily = interFamily,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 34.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = onClick, modifier = Modifier.size(24.dp)) {
                        Icon(
                            Icons.Rounded.AddCircle,
                            contentDescription = "Add product to category",
                            tint = Color1
                        )
                    }
                }
            }
        }
    }
}


