package com.cuan.catatankeuangan.presentation.screens.books

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.CategoryWithProducts
import com.cuan.catatankeuangan.presentation.components.CustomTextField
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.components.WarningDialog
import com.cuan.catatankeuangan.presentation.screens.product.category.CategoryItem
import com.cuan.catatankeuangan.presentation.screens.product.category.CategorySelectProductSheet
import com.cuan.catatankeuangan.presentation.screens.product.category.EditCategoryDialog
import com.cuan.catatankeuangan.presentation.screens.product.category.NewCategoryDialog
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.viewmodel.ProductViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DigitalBookBookList(
//    digimon: Boolean,
//    onDismiss: () -> Unit
//) {
//    var bookAdder by remember {
//        mutableStateOf(false)
//    }
//
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
//
//    val customTopPadding = getCustomTopPadding(16.dp)
//    if (digimon) {
//        Dialog(
//            onDismissRequest = onDismiss,
//            properties = DialogProperties(usePlatformDefaultWidth = false)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(MainBgColor)
//            ) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    TopBar(
//                        onClick = onDismiss,
//                        text = "Buku Digital"
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Card(
//                        colors = CardDefaults.cardColors(
//                            containerColor = Color1,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(24),
//                        modifier = Modifier
//                            .height(48.dp)
//                            .fillMaxWidth()
//                            .padding(horizontal = 36.dp)
//                            .clickable { bookAdder = true }
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center,
//                            modifier = Modifier.fillMaxSize()
//                        ) {
//                            Text(
//                                text = "Baru",
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White,
//                                textAlign = TextAlign.Center,
//                                modifier = Modifier
//                            )
//
//                            Spacer(modifier = Modifier.width(2.dp))
//
//                            Icon(
//                                painter = painterResource(R.drawable.rounded_add),
//                                contentDescription = "New book",
//                                modifier = Modifier
//                                    .width(16.dp)
//                            )
//                        }
//                    }
//                }
//            }
//            if (bookAdder) {
//                NewBookBottomSheet(
//                    sheetState = sheetState,
//                    onDismiss = { /*TODO*/ }
//                )
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NewBookBottomSheet(onDismiss: () -> Unit, sheetState: SheetState) {
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        sheetState = sheetState,
//        containerColor = MainBgColor,
//        modifier = Modifier.fillMaxHeight()
//    ) {
//
//    }
//}
//
//@Composable
//fun BookListItem(book: String, isExpanded: Boolean, onClick: () -> Unit) {
//    Card(
//        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color1),
//        shape = RoundedCornerShape(24),
//        border = BorderStroke(1.dp, Color1),
//        modifier = Modifier
//            .height(48.dp)
//            .fillMaxWidth()
//            .padding(horizontal = 36.dp)
//            .clickable { /*TODO*/ }
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Icon(painter = painterResource(R.drawable.asians), contentDescription = "")
//
//            Text(
//                text = book,
//                fontSize = 18.sp,
//                color = Color1,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//            )
//
//            Text(
//                text = "8",
//                fontSize = 16.sp,
//                color = Color1,
//                modifier = Modifier.padding(horizontal = 12.dp)
//            )
//        }
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BookList(
////    bookViewModel: BookViewModel,
//    showDialog: Boolean,
//    onDismiss: () -> Unit
//) {
//
//    var showNewCategory by remember { mutableStateOf(false) }
//    var showEditCategory by remember { mutableStateOf(false) }
//    var showDeleteCategory by remember { mutableStateOf(false) }
//
//    var showProductSheet by remember { mutableStateOf(false) }
//    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = false
//    )
//
//    if (showDialog) {
//        Dialog(
//            onDismissRequest = onDismiss,
//            properties = DialogProperties(usePlatformDefaultWidth = false)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(MainBgColor)
//            ) {
//                CategorySelectProductSheet(
//                    showSheet = showProductSheet,
//                    sheetState = sheetState,
//                    onDismiss = { showProductSheet = false },
//                    productViewModel = productViewModel,
//                    categoryId = selectedCategory?.category?.id
//                )
//                Column {
//                    TopBar(onClick = onDismiss, text = "Kategori")
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    LazyColumn(
//                        verticalArrangement = Arrangement.spacedBy(12.dp),
//                        modifier = Modifier.padding(36.dp, 0.dp, 36.dp, 12.dp)
//                    ) {
//
//                        if (categories.isEmpty()) {
//                            item {
//                                Text(
//                                    text = "Belum ada kategori. Klik tombol Baru untuk menambahkan kategori baru. ",
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//                            }
//                        } else {
//                            items(categories) { category ->
//                                CategoryItem(
//                                    category,
//                                    productViewModel,
//                                    onEditClick = {
//                                        showEditCategory = true
//                                        selectedCategory = category
//                                    },
//                                    onDeleteClick = {
//                                        showDeleteCategory = true
//                                        selectedCategory = category
//                                    },
//                                    onAddClick = {
//                                        showProductSheet = true
//                                        selectedCategory = category
//                                    }
//                                )
//                            }
//                        }
//
//                        item {
//                            Card(
//                                colors = CardDefaults.cardColors(
//                                    containerColor = Color1,
//                                    contentColor = Color.White
//                                ),
//                                shape = RoundedCornerShape(24),
//                                modifier = Modifier
//                                    .height(48.dp)
//                                    .fillMaxWidth()
//                                    .clickable { showNewCategory = true }
//                            ) {
//                                Row(
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    horizontalArrangement = Arrangement.Center,
//                                    modifier = Modifier.fillMaxSize()
//                                ) {
//                                    Text(
//                                        text = "Baru",
//                                        fontSize = 18.sp,
//                                        fontWeight = FontWeight.SemiBold,
//                                        color = Color.White,
//                                        textAlign = TextAlign.Center,
//                                        modifier = Modifier
//                                    )
//
//                                    Spacer(modifier = Modifier.width(2.dp))
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.rounded_add),
//                                        contentDescription = "New category",
//                                        modifier = Modifier
//                                            .width(16.dp)
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}