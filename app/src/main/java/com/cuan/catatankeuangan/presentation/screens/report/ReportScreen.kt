package com.cuan.catatankeuangan.presentation.screens.report

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.domain.model.CategoryReport
import com.cuan.catatankeuangan.domain.model.ProductReportRejection
import com.cuan.catatankeuangan.presentation.components.CategoryFilter
import com.cuan.catatankeuangan.presentation.components.DatePickerField
import com.cuan.catatankeuangan.presentation.components.InfoCard
import com.cuan.catatankeuangan.presentation.components.ProductCard
import com.cuan.catatankeuangan.presentation.components.ReportCard
import com.cuan.catatankeuangan.presentation.components.ReportCategoryCard
import com.cuan.catatankeuangan.presentation.screens.category.ProductCategory
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.OptionalColor3
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.viewmodel.ProductViewModel
import com.cuan.catatankeuangan.viewmodel.ReportViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    bottomNavHeight: Dp,
    reportViewModel: ReportViewModel,
    productViewModel: ProductViewModel
) {
    val categoryList by productViewModel.allCategories.observeAsState(initial = emptyList())
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    val report by reportViewModel.salesReport.collectAsState()
//    val categoryReport by reportViewModel.categoryReport.collectAsState()
//    val totalIncome by reportViewModel.totalIncome.collectAsState()
//    val totalExpense by reportViewModel.totalExpense.collectAsState()

    var textFieldValue by remember { mutableStateOf("") }
    var showCategory by remember { mutableStateOf(false) }
    var isRange by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    val totalRevenue = report.sumOf { it.totalRevenue }
    val totalProfit = report.sumOf { it.totalProfit }

    val gridState = rememberLazyStaggeredGridState()
    val customTopPadding = getCustomTopPadding(16.dp)

    var selectedReport by remember { mutableStateOf<ProductReportRejection?>(null) }
    var showReportDetails by remember { mutableStateOf(false) }

    val bookType = "bisnis"

    ProductCategory(
        productViewModel = productViewModel,
        showDialog = showCategory,
        onDismiss = { showCategory = false }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(0.dp, customTopPadding, 0.dp, bottomNavHeight),
    ) {
        selectedReport?.let {
            ReportDetail(
                productReportRejection = it,
                showDialog = showReportDetails,
                onDismiss = { showReportDetails = false }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Laporan",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            if (bookType == "bisnis") {
                // bisnis screen
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Rentang Waktu",
                        color = Color1
                    )
                    Switch(
                        checked = isRange,
                        onCheckedChange = { isRange = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color1,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = OptionalColor3
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isRange) {
                    // switch is on
                    selectedDate = null

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color1),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "icon",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        DatePickerField(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f),
                            placeholder = "Mulai",
                            date = startDate,
                            onDateSelected = {
                                startDate = it
                                reportViewModel.setDateRange(startDate, endDate)
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color1),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "icon",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        DatePickerField(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f),
                            placeholder = "Selesai",
                            date = endDate,
                            onDateSelected = {
                                endDate = it
                                reportViewModel.setDateRange(startDate, endDate)
                            }
                        )
                    }
                } else {
                    // switch is off
                    startDate = null
                    endDate = null

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color1),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "icon",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        DatePickerField(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f),
                            placeholder = "Pilih Tanggal",
                            date = selectedDate,
                            onDateSelected = {
                                selectedDate = it
                                reportViewModel.setSingleDateFilter(it)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * omset & laba section
                 */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    /* omset */
                    InfoCard(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(percent = 45))
                            .background(Color1),
                        image = painterResource(R.drawable.money_bag),
                        title = "Omset",
                        value = formatAsCurrency(totalRevenue)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    /* laba */
                    InfoCard(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(percent = 45))
                            .background(Color1),
                        image = painterResource(R.drawable.finance_colored),
                        title = "Laba",
                        value = formatAsCurrency(totalProfit)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // search field
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        reportViewModel.setProductName(it)
                    },
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
                        onCategorySelected = {
                            selectedCategoryId = it
                            reportViewModel.setCategoryFilter(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (report.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada produk. Silahkan ke halaman produk untuk menambahkan produk baru.",
                            textAlign = TextAlign.Center,
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
                        items(report) { reportItem ->
                            ReportCard(
                                productReportRejection = reportItem,
                                onClick = {
                                    showReportDetails = true
                                    selectedReport = reportItem
                                }
                            )
                        }
                    }
                }

            } else if (bookType == "pribadi") {
                /**
                 * pribadi screen
                 */
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Rentang Waktu",
                        color = Color1
                    )
                    Switch(
                        checked = isRange,
                        onCheckedChange = { isRange = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color1,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = OptionalColor3
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isRange) {
                    // switch is on
                    selectedDate = null

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color1),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "icon",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        DatePickerField(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f),
                            placeholder = "Mulai",
                            date = startDate,
                            onDateSelected = {
                                startDate = it
                                reportViewModel.setDateRange(startDate, endDate)
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color1),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "icon",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        DatePickerField(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f),
                            placeholder = "Selesai",
                            date = endDate,
                            onDateSelected = {
                                endDate = it
                                reportViewModel.setDateRange(startDate, endDate)
                            }
                        )
                    }
                } else {
                    // switch is off
                    startDate = null
                    endDate = null

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color1),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "icon",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        DatePickerField(
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f),
                            placeholder = "Pilih Tanggal",
                            date = selectedDate,
                            onDateSelected = {
                                selectedDate = it
                                reportViewModel.setSingleDateFilter(it)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * pemasukan & pengeluaran section
                 */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    /* pemasukan */
                    InfoCard(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(percent = 45))
                            .background(Color1),
                        image = painterResource(R.drawable.arrow_up),
                        title = "Pemasukan",
                        titleSize = 12.sp,
                        value = formatAsCurrency(0L)
//                        value = formatAsCurrency(totalIncome.toLong())
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    /* pengeluaran */
                    InfoCard(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(percent = 45))
                            .background(Color3),
                        image = painterResource(R.drawable.arrow_up),
                        title = "Pengeluaran",
                        titleSize = 12.sp,
                        value = formatAsCurrency(0L)
//                        value = formatAsCurrency(totalExpense.toLong())
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // search field
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it

                    },
                    placeholder = {
                        Text(
                            text = "Telusuri Kategori",
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

                if (report.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada kategori. Silahkan untuk menambahkan kategori baru.",
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
//                    LazyVerticalStaggeredGrid(
//                        state = gridState,
//                        columns = StaggeredGridCells.Adaptive(160.dp),
//                        verticalItemSpacing = 10.dp,
//                        horizontalArrangement = Arrangement.spacedBy(10.dp),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .padding(vertical = 6.dp)
//                    ) {
//                        items(categoryReport) { reportItem ->
//                            ReportCategoryCard(
//                                categoryReport = reportItem,
//                            )
//                        }
                }
            }

        }
    }
}
