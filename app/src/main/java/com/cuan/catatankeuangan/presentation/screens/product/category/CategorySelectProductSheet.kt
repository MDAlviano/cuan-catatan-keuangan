package com.cuan.catatankeuangan.presentation.screens.product.category

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.components.CategoryProductCard
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectProductSheet(
    showSheet: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    productViewModel: ProductViewModel,
    categoryId: Int?
) {
    val context = LocalContext.current

    var textFieldValue by remember { mutableStateOf("") }

    val productList by productViewModel.allProducts.observeAsState(initial = emptyList())

    val filteredProducts = productList.filter { product ->
        val matchesSearch =
            textFieldValue.isBlank() || product.name.contains(textFieldValue, ignoreCase = true)
        val uncategorized = product.categoryId == null
        matchesSearch && uncategorized
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = MainBgColor,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                            .weight(1f)
                            .height(56.dp)
                            .shadow(elevation = 0.2.dp, shape = RoundedCornerShape(10.dp)),
                    )

                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(15),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color1,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(10.dp),
                        modifier = Modifier
                            .height(56.dp)
                            .aspectRatio(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.scan_barcode),
                            contentDescription = "Scan product",
                            modifier = Modifier
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (filteredProducts.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Produk tidak ditemukan.",
                            textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                } else {
                    LazyVerticalGrid(
//                    state = gridState,
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                    ) {
                        items(filteredProducts) { product ->
                            CategoryProductCard(
                                product = product,
                                onClick = {
                                    productViewModel.addProductToCategory(
                                        product.id,
                                        categoryId = categoryId
                                    )
                                    Toast.makeText(
                                        context,
                                        "Produk ${product.name} ditambahkan ke kategori.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}