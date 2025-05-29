package com.cuan.catatankeuangan.presentation.screens.category

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.CategoryWithProducts
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.components.WarningDialog
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.interFamily
import com.cuan.catatankeuangan.presentation.utils.formatAbbreviatedNominal
import com.cuan.catatankeuangan.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCategory(
    productViewModel: ProductViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val categories by productViewModel.allCategoryWithProducts.observeAsState(initial = emptyList())
    var selectedCategory by remember { mutableStateOf<CategoryWithProducts?>(null) }

    var showNewCategory by remember { mutableStateOf(false) }
    var showEditCategory by remember { mutableStateOf(false) }
    var showDeleteCategory by remember { mutableStateOf(false) }

    var showProductSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    if (showDialog) {
        NewCategoryDialog(
            productViewModel = productViewModel,
            showDialog = showNewCategory,
            onDismiss = { showNewCategory = false }
        )

        selectedCategory?.let {
            EditCategoryDialog(
                category = it,
                productViewModel = productViewModel,
                showDialog = showEditCategory,
                onDismiss = { showEditCategory = false }
            )
        }

        selectedCategory?.let {
            WarningDialog(
                showDialog = showDeleteCategory,
                onDismiss = { showDeleteCategory = false },
                onConfirm = {
                    productViewModel.deleteCategory(selectedCategory!!.category)
                    showDeleteCategory = false
                },
                headerText = "Hapus Kategori",
                bodyText = "Apakah Anda yakin ingin menghapus kategori ${selectedCategory!!.category.name}",
                confirmText = "Hapus"
            )
        }

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBgColor)
            ) {
                CategorySelectProductSheet(
                    showSheet = showProductSheet,
                    sheetState = sheetState,
                    onDismiss = { showProductSheet = false },
                    productViewModel = productViewModel,
                    categoryId = selectedCategory?.category?.id
                )
                Column {
                    TopBar(onClick = onDismiss, text = "Kategori")

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(36.dp, 0.dp, 36.dp, 12.dp)
                    ) {

                        if (categories.isEmpty()) {
                            item {
                                Text(
                                    text = "Belum ada kategori. Klik tombol Baru untuk menambahkan kategori baru. ",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        } else {
                            items(categories) { category ->
                                CategoryItem(
                                    category,
                                    productViewModel,
                                    onEditClick = {
                                        showEditCategory = true
                                        selectedCategory = category
                                    },
                                    onDeleteClick = {
                                        showDeleteCategory = true
                                        selectedCategory = category
                                    },
                                    onAddClick = {
                                        showProductSheet = true
                                        selectedCategory = category
                                    }
                                )
                            }
                        }

                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color1,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(24),
                                modifier = Modifier
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .clickable { showNewCategory = true }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Baru",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                    )

                                    Spacer(modifier = Modifier.width(2.dp))

                                    Icon(
                                        painter = painterResource(R.drawable.rounded_add),
                                        contentDescription = "New category",
                                        modifier = Modifier
                                            .width(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryWithProducts,
    productViewModel: ProductViewModel,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit, // delete category
    onAddClick: () -> Unit,
) {
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

    val conditionalColor = if (expanded) Color1 else Color.White
    val conditionalColor2 = if (expanded) Color.White else Color1

    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = conditionalColor,
                contentColor = conditionalColor2
            ),
            shape = RoundedCornerShape(24),
            border = BorderStroke(1.dp, Color1),
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowDown
                        else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Expand"
                    )
                }

                Text(
                    text = category.category.name,
                    fontSize = 18.sp,
                    color = conditionalColor2,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = formatAbbreviatedNominal(category.products.size.toLong()),
                    fontSize = 16.sp,
                    fontFamily = interFamily,
                    color = conditionalColor2,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        if (expanded) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(bottomStart = 25f, bottomEnd = 25f)
                    )
                    .border(
                        width = 1.dp,
                        color = Color1,
                        shape = RoundedCornerShape(bottomStart = 25f, bottomEnd = 25f)
                    )
            ) {
                if (category.products.isNotEmpty()) {
                    category.products.forEach { product ->
                        CategorizedProductItem(
                            product,
                            onDelete = {
                                productViewModel.removeProductFromCategory(product.id)

                                Toast.makeText(
                                    context,
                                    "Produk berhasil dihapus dari kategori.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    }
                } else {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Color1
                        ),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Text(
                            text = "Belum ada produk.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                    HorizontalDivider(color = Color1)
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color1
                    ),
                    shape = RoundedCornerShape(bottomStart = 25f, bottomEnd = 25f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAddClick() }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        Text(
                            text = "Tambah produk",
                            color = Color1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.rounded_add),
                            contentDescription = "Add product to category",
                            tint = Color1,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Button(
                    onClick = onEditClick,
                    shape = RoundedCornerShape(25f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color1,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Ubah nama",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit category name",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Button(
                    onClick = onDeleteClick,
                    shape = RoundedCornerShape(25f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color3,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Hapus",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete category",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategorizedProductItem(product: Product, onDelete: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color1
        ),
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = if (product.imageUri != null) {
                    rememberAsyncImagePainter(model = product.imageUri)
                } else {
                    painterResource(
                        id = R.drawable.product1
                    )
                },
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20))
            )
            Text(
                text = product.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete product from category",
                    tint = Color3,
                )
            }
        }
    }
    HorizontalDivider(color = Color1)
}
