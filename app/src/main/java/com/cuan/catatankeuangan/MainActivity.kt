package com.cuan.catatankeuangan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.cuan.catatankeuangan.presentation.screens.main.MainScreen
import com.cuan.catatankeuangan.presentation.theme.CuanTheme
import com.cuan.catatankeuangan.viewmodel.BookViewModel
import com.cuan.catatankeuangan.viewmodel.ProductViewModel
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel

class MainActivity : ComponentActivity() {
    private val transactionViewModel: TransactionViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuanTheme {
                MainScreen(transactionViewModel, productViewModel, bookViewModel)
            }
        }
    }
}