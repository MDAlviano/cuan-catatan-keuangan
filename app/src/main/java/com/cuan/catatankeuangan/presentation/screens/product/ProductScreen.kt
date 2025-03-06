package com.cuan.catatankeuangan.presentation.screens.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.components.CategoryFilter
import com.cuan.catatankeuangan.presentation.components.DeleteProductDialog
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.viewmodel.FilterViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(bottomNavHeight: Dp) {

    val customTopPadding = getCustomTopPadding(16.dp)

    var textFieldValue by remember { mutableStateOf("") }

    var showDeletePopup by remember { mutableStateOf(true) }

    val gridState = rememberLazyGridState()
    var isFabVisible by remember { mutableStateOf(true) }

    // Hide fab when scrolling
    LaunchedEffect(gridState) {
        var job: Job? = null
        snapshotFlow { gridState.firstVisibleItemScrollOffset }
            .collectLatest { scrollOffset ->
                if (scrollOffset > 0) {
                    isFabVisible = false // Hide fab
                    job?.cancel()

                    // Show again after delay
                    job = launch {
                        delay(2000)
                        isFabVisible = true
                    }
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(0.dp, customTopPadding, 0.dp, bottomNavHeight),
    ) {
        AnimatedVisibility(
            visible = isFabVisible,
            enter = fadeIn() + slideInHorizontally { it },
            exit = fadeOut() + slideOutHorizontally { it },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .zIndex(100F)
        ) {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color2,
                shape = RoundedCornerShape(20)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah transaksi", tint = Color.White)
            }
        }

        DeleteProductDialog(
            showDeletePopup = showDeletePopup,
            onDismiss = { showDeletePopup = false },
            onConfirm = { showDeletePopup = false }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Daftar Produk",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_product),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color2,
                    cursorColor = Color2
                ),
                leadingIcon = { Icon(Icons.Default.Search, "test", tint = Color2) },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 0.5.dp, shape = RoundedCornerShape(10.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filter bar
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = CircleShape,
                    contentPadding = PaddingValues(all = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color1,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(0.dp)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Category button")
                }

                Spacer(modifier = Modifier.width(6.dp))

                CategoryFilter(viewModel = FilterViewModel())
            }

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(160.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 6.dp)
            ) {
                items(9) {
                    ProductCard(onClick = { showDeletePopup = true })
                }
            }
        }
    }
}

@Composable
fun ProductList() {

}

@Composable
fun ProductCard(onClick: () -> Unit) {

    var actionExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier
            .fillMaxHeight()
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
