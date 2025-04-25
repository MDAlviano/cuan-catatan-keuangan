package com.cuan.catatankeuangan.presentation.screens.product.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.presentation.components.TopBar
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.viewmodel.ProductViewModel

@Composable
fun ProductCategory(
    productViewModel: ProductViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {

    val categories by productViewModel.allCategories.observeAsState(initial = emptyList())

    val isExpanded by remember { mutableStateOf(false) }

    var showNewCategory by remember { mutableStateOf(false) }

    if (showDialog) {

        NewCategoryDialog(
            productViewModel = productViewModel,
            showDialog = showNewCategory,
            onDismiss = { showNewCategory = false }
        )

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBgColor)
            ) {
                Column {
                    TopBar(onClick = onDismiss, text = "Kategori")

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        items(categories) { category ->
                            CategoryListItem(category.name, isExpanded, onClick = {})
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color1,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(24),
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp)
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

@Composable
fun CategoryListItem(category: String, isExpanded: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color1),
        shape = RoundedCornerShape(24),
        border = BorderStroke(1.dp, Color1),
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
            .clickable { /*TODO*/ }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Expand")
            }

            Text(
                text = category,
                fontSize = 18.sp,
                color = Color1,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )

            Text(
                text = "8",
                fontSize = 16.sp,
                color = Color1,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}
