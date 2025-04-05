package com.cuan.catatankeuangan.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency

@Composable
fun ProductCard(onClick: () -> Unit) {

    var actionExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier
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
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.product1),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10))
                )
                Text(
                    text = "Stok: 69",
                    fontSize = 12.sp,
                    fontFamily = outfitFamily,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp, 6.dp)
                        .background(Color1, RoundedCornerShape(50))
                        .padding(6.dp, 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp, 4.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = "Sunglasses",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp
                )
                Text(
                    text = "Aksesoris",
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
                        text = formatAsCurrency(10000),
                        fontSize = 18.sp,
                        fontFamily = outfitFamily,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 34.sp
                    )

                    // Product card dropdown menu
                    Box(
                        modifier = Modifier.padding(start = 16.dp)
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
                                onClick = { /*TODO*/ },
                            )

                            DropdownMenuItem(
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.Refresh,
                                        contentDescription = "Edit product",
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
                                        Icons.Default.Delete,
                                        contentDescription = "Edit product",
                                        modifier = Modifier.width(18.dp)
                                    )
                                },
                                text = { Text(text = "Hapus", color = Color3) },
                                colors = MenuDefaults.itemColors(
                                    trailingIconColor = Color3
                                ),
                                onClick = {
                                    actionExpanded = false
                                    onClick()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}