package com.cuan.catatankeuangan.presentation.screens.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.TransactionType
import com.cuan.catatankeuangan.presentation.components.AbbreviatedNominalText
import com.cuan.catatankeuangan.presentation.components.AutoResizedText
import com.cuan.catatankeuangan.presentation.components.BalanceDetailsDialog
import com.cuan.catatankeuangan.presentation.components.TransactionCard
import com.cuan.catatankeuangan.presentation.screens.newtransaction.NewTransactionDialog
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.Color3
import com.cuan.catatankeuangan.presentation.theme.MainBgColor
import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.presentation.utils.getDate
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(bottomNavHeight: Dp, transactionViewModel: TransactionViewModel) {

    val customTopPadding = getCustomTopPadding(16.dp)

    val transactions by transactionViewModel.allTransactions.observeAsState(initial = emptyList())
    val totalSaldo by transactionViewModel.totalSaldo.observeAsState(initial = 0L)
    val totalPemasukan by transactionViewModel.totalPemasukan.observeAsState(initial = 0L)
    val totalPengeluaran by transactionViewModel.totalPengeluaran.observeAsState(initial = 0L)

    var applyFilter by remember { mutableStateOf<TransactionType?>(null) }

    val filteredTransactions = transactions.filter {
        applyFilter == null || it.transactionType == applyFilter
    }

    val groupedTransactions = filteredTransactions.groupBy { getDate(it.timestamp) }

    var showDialog by remember { mutableStateOf(false) }
    var showBalanceDetails by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    var isFabVisible by remember { mutableStateOf(true) }

    // Hide fab when scrolling
    LaunchedEffect(listState) {
        var job: Job? = null
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collectLatest { scrollOffset ->
                if (scrollOffset > 0) {
                    isFabVisible = false // Hide fab
                    job?.cancel()

                    // Show again after delay
                    job = launch {
                        delay(1000)
                        isFabVisible = true
                    }
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBgColor)
            .padding(0.dp, customTopPadding, 0.dp, bottomNavHeight)
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
                onClick = { showDialog = true },
                containerColor = Color2,
                shape = RoundedCornerShape(20)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah transaksi", tint = Color.White)
            }
        }

        BalanceDetailsDialog(
            showBalanceDetails = showBalanceDetails,
            onDismiss = { showBalanceDetails = false },
            text = "Detail Saldo",
            balance = formatAsCurrency(totalSaldo),
            income = formatAsCurrency(totalPemasukan),
            expense = formatAsCurrency(totalPengeluaran)
        )

        NewTransactionDialog(
            transactionViewModel,
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = { showDialog = false }
        )


        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Riwayat Transaksi",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { showBalanceDetails = true }
                        )
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = "Balance",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Saldo",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                AutoResizedText(
                    text = formatAsCurrency(totalSaldo),
                    fontSize = 18.sp,
                    style = TextStyle(
                        fontFamily = outfitFamily,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 64.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedCard(
                    colors = if (applyFilter == TransactionType.MASUK) {
                        CardDefaults.cardColors(containerColor = Color1)
                    } else {
                        CardDefaults.cardColors(containerColor = Color.Transparent)
                    },
                    border = BorderStroke(1.dp, Color1),
                    shape = RoundedCornerShape(25),
                    modifier = Modifier
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { showBalanceDetails = true },
                                onTap = {
                                    applyFilter =
                                        if (applyFilter == TransactionType.MASUK) null else TransactionType.MASUK
                                }
                            )
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_up),
                            contentDescription = "Pemasukan",
                            tint = if (applyFilter == TransactionType.MASUK) {
                                Color1
                            } else {
                                Color.White
                            },
                            modifier = Modifier
                                .size(28.dp)
                                .rotate(180F)
                                .background(
                                    if (applyFilter == TransactionType.MASUK) {
                                        Color.White
                                    } else {
                                        Color1
                                    },
                                    shape = RoundedCornerShape(100)
                                )
                                .padding(6.dp)
                        )

                        Column {
                            val textColor = if (applyFilter == TransactionType.MASUK) {
                                Color.White
                            } else {
                                Color1
                            }
                            Text(
                                text = "Pemasukan",
                                color = textColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )

                            HorizontalDivider(color = textColor)

                            AbbreviatedNominalText(
                                value = totalPemasukan,
                                color = textColor,
                                style = TextStyle(fontSize = 20.sp),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }

                OutlinedCard(
                    colors = if (applyFilter == TransactionType.KELUAR) {
                        CardDefaults.cardColors(containerColor = Color3)
                    } else {
                        CardDefaults.cardColors(containerColor = Color.Transparent)
                    },
                    border = BorderStroke(1.dp, Color3),
                    shape = RoundedCornerShape(25),
                    modifier = Modifier
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { showBalanceDetails = true },
                                onTap = {
                                    applyFilter =
                                        if (applyFilter == TransactionType.KELUAR) null else TransactionType.KELUAR
                                }
                            )
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_up),
                            contentDescription = "Pengeluaran",
                            tint = if (applyFilter == TransactionType.KELUAR) {
                                Color3
                            } else {
                                Color.White
                            },
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    if (applyFilter == TransactionType.KELUAR) {
                                        Color.White
                                    } else {
                                        Color3
                                    },
                                    shape = RoundedCornerShape(100)
                                )
                                .padding(6.dp)
                        )

                        Column {
                            val textColor = if (applyFilter == TransactionType.KELUAR) {
                                Color.White
                            } else {
                                Color3
                            }
                            Text(
                                text = "Pengeluaran",
                                color = textColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )

                            HorizontalDivider(color = textColor)

                            AbbreviatedNominalText(
                                value = totalPengeluaran,
                                color = textColor,
                                style = TextStyle(fontSize = 20.sp),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (transactions.isEmpty()) {
                Text(
                    text = "Belum ada transaksi. Ketuk tanda + untuk menambahkan transaksi baru.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    groupedTransactions.forEach { (date, transactionList) ->
                        item {
                            Text(
                                text = date,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontFamily = outfitFamily,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        items(transactionList) { transaction ->
                            TransactionCard(
                                transaction,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun HistoryScreenPreview() {
//    HistoryScreen()
//}