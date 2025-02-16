package com.cuan.catatankeuangan.presentation.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.cuan.catatankeuangan.R
import com.cuan.catatankeuangan.data.local.entities.Transaction
import com.cuan.catatankeuangan.presentation.components.AutoResizedText
import com.cuan.catatankeuangan.presentation.components.TransactionCard
import com.cuan.catatankeuangan.presentation.screens.newtransaction.NewTransactionDialog
import com.cuan.catatankeuangan.presentation.theme.Color1
import com.cuan.catatankeuangan.presentation.theme.Color2
import com.cuan.catatankeuangan.presentation.theme.Color2Color1Vert
import com.cuan.catatankeuangan.presentation.theme.Color5
import com.cuan.catatankeuangan.presentation.utils.formatAsCurrency
import com.cuan.catatankeuangan.presentation.utils.getCustomTopPadding
import com.cuan.catatankeuangan.presentation.theme.outfitFamily
import com.cuan.catatankeuangan.presentation.utils.getCustomBottomPadding
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen( bottomNavHeight: Dp,transactionViewModel: TransactionViewModel) {
    val transactions by transactionViewModel.allTransactions.observeAsState(initial = emptyList())
    val totalSaldo by transactionViewModel.totalSaldo.observeAsState(initial = 0L)
    val totalPemasukan by transactionViewModel.totalPemasukan.observeAsState(initial = 0L)
    val totalPengeluaran by transactionViewModel.totalPengeluaran.observeAsState(initial = 0L)

    Log.d("bottomNavHeight", "HomeScreen bottom nav: $bottomNavHeight")

    val customTopPadding = getCustomTopPadding(24.dp)

    var showDialog by remember { mutableStateOf(false) }

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

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 0.dp, 0.dp, bottomNavHeight)
        .background(Color.White)) {
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

        NewTransactionDialog(
            transactionViewModel,
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = { showDialog = false }
        )

        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color2Color1Vert)
                    .padding(24.dp, customTopPadding, 24.dp, 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.wallet),
                                contentDescription = "Saldo",
                                tint = Color(0xFF979797),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Saldo",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF979797),
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = formatAsCurrency(totalSaldo),
                            fontFamily = outfitFamily,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White

                        )
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        contentPadding = PaddingValues(12.dp, 0.dp, 4.dp, 0.dp),
                        shape = RoundedCornerShape(15),
                        colors = ButtonColors(Color2, Color.White, Color2, Color2),
                        modifier = Modifier
                            .widthIn(0.dp, 140.dp)
                            .heightIn(0.dp, 30.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Toko A",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier.widthIn(0.dp, 120.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.drop_arrow_down),
                                contentDescription = "",
                                modifier = Modifier.width(IntrinsicSize.Max)
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .align(Alignment.BottomCenter)
                        .offset(y = 50.dp)
                        .padding(20.dp, 40.dp, 20.dp, 0.dp)
                        .shadow(3.dp, RoundedCornerShape(8))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8))
                            .padding(0.dp, 12.dp, 0.dp, 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .background(Color2, RoundedCornerShape(15))
                                .padding(14.dp, 4.dp),
                            text = "Hari ini",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .widthIn(0.dp, 120.dp)
                                    .fillMaxHeight()
                            ) {
                                Text(
                                    text = "Pemasukan",
                                    fontSize = 14.sp,
                                    color = Color1,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                AutoResizedText(
                                    text = formatAsCurrency(totalPemasukan),
                                    fontSize = 22.sp,
                                    customStyle = TextStyle(
                                        fontFamily = outfitFamily,
                                        textAlign = TextAlign.Center
                                    ),
                                )
                            }
                            VerticalDivider()
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.widthIn(0.dp, 120.dp)
                            ) {
                                Text(
                                    text = "Pengeluaran",
                                    fontSize = 14.sp,
                                    color = Color5,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                AutoResizedText(
                                    text = formatAsCurrency(totalPengeluaran),
                                    fontSize = 22.sp,
                                    customStyle = TextStyle(
                                        fontFamily = outfitFamily,
                                        textAlign = TextAlign.Center
                                    ),
                                )
                            }
                        }
                    }
                }
            }
            HomeTransactions(transactions, listState)
        }
    }
}

@Composable
fun HomeTransactions(transactions: List<Transaction>, listState: LazyListState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 56.dp, 24.dp, 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Transaksi terkini",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            modifier = Modifier.clickable { },
            text = "Lebih detail",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF979797),
        )
    }
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        items(transactions) { transaction ->
            TransactionCard(transaction)
        }
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}