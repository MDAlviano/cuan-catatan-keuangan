package com.cuan.catatankeuangan.presentation.screens.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.presentation.components.CategoryFilter
import com.cuan.catatankeuangan.presentation.components.DeleteProductDialog
import com.cuan.catatankeuangan.presentation.components.ProductCard
import com.cuan.catatankeuangan.presentation.screens.product.category.ProductCategory
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.viewmodel.ProductViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(bottomNavHeight: Dp, productViewModel: ProductViewModel) {
    val productList by productViewModel.allProducts.observeAsState(initial = emptyList())
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val categoryList by productViewModel.allCategories.observeAsState(initial = emptyList())
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    val filteredProducts = if (selectedCategoryId == null) {
        productList
    } else {
        productList.filter { it.categoryId == selectedCategoryId }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val customTopPadding = getCustomTopPadding(16.dp)

    var textFieldValue by remember { mutableStateOf("") }

    var showCategory by remember { mutableStateOf(false) }
    var showAddProductDialog by remember { mutableStateOf(false) }
    var showEditProductDialog by remember { mutableStateOf(false) }
    var showDeletePopup by remember { mutableStateOf(false) }

    val gridState = rememberLazyStaggeredGridState()
    var isFabVisible by remember { mutableStateOf(true) }

    val bookType = "bisnis"

//    if (bookType == "bisnis") {
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

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(81F)
        )

        if (bookType == "bisnis") {
            AnimatedVisibility(
                visible = isFabVisible,
                enter = fadeIn() + slideInHorizontally { it },
                exit = fadeOut() + slideOutHorizontally { it },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .zIndex(80F)
            ) {
                FloatingActionButton(
                    onClick = { showAddProductDialog = true },
                    containerColor = Color2,
                    shape = RoundedCornerShape(20)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Tambah transaksi",
                        tint = Color.White
                    )
                }
            }
        }

        ProductCategory(
            productViewModel = productViewModel,
            showDialog = showCategory,
            onDismiss = { showCategory = false }
        )

        NewProduct(
            showDialog = showAddProductDialog,
            onDismiss = { showAddProductDialog = false },
            productViewModel = productViewModel
        )

        selectedProduct?.let {
            EditProduct(
                product = it,
                productViewModel = productViewModel,
                showDialog = showEditProductDialog,
                onDismiss = { showEditProductDialog = false })
        }

        DeleteProductDialog(
            showDeletePopup = showDeletePopup,
            onDismiss = { showDeletePopup = false },
            onConfirm = {
                selectedProduct?.let { product ->
                    productViewModel.deleteProduct(product) {
                        // Called if not undone
                    }

                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Produk ${product.name} dihapus",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            productViewModel.undoDeleteProduct()
                        }
                    }
                }
                showDeletePopup = false
            }
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

            if (bookType == "bisnis") {
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
                    leadingIcon = { Icon(Icons.Default.Search, "Search", tint = Color2) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 0.5.dp, shape = RoundedCornerShape(10.dp)),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Filter bar
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { showCategory = true },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color1,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.filter),
                            contentDescription = "Category button"
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    CategoryFilter(
                        categories = categoryList,
                        selectedCategoryId = selectedCategoryId,
                        onCategorySelected = { selectedCategoryId = it}
                    )
                }

                if (filteredProducts.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Belum ada produk. Ketuk tanda + untuk menambahkan produk baru.",
                            textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        state = gridState,
                        columns = StaggeredGridCells.Adaptive(160.dp),
                        verticalItemSpacing = 10.dp,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(vertical = 6.dp)
                    ) {
                        items(filteredProducts) { product ->
                            ProductCard(
                                product = product,
                                onEditClick = {
                                    showEditProductDialog = true
                                    selectedProduct = product
                                },
                                onDeleteClick = {
                                    showDeletePopup = true
                                    selectedProduct = product
                                },
                                category = categoryList.find { it.id == product.categoryId }?.name
                                    ?: "Tanpa kategori",
                            )
                        }
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Maaf, halaman ini hanya dapat diakses pada buku dengan jenis Bisnis.",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Jenis buku Anda: $bookType.",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
//    } else  {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Text(text = "You cannot access this page.")
//        }
//    }
}


