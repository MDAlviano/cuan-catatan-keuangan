package com.cuan.catatankeuangan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.presentation.screens.main.MainScreen
import com.cuan.catatankeuangan.presentation.theme.CuanTheme
import com.cuan.catatankeuangan.repository.BackupManager
import com.cuan.catatankeuangan.repository.CloudinaryService
import com.cuan.catatankeuangan.viewmodel.BookViewModel
import com.cuan.catatankeuangan.viewmodel.ProductViewModel
import com.cuan.catatankeuangan.viewmodel.TransactionViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File


class MainActivity : ComponentActivity() {
    private val transactionViewModel: TransactionViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private val bookViewModel: BookViewModel by viewModels()

    private lateinit var backupManager: BackupManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val dao = MainDatabase.getInstance(this).productDao()
        val cloudinary = CloudinaryService(context = this)
        val firestore = FirebaseFirestore.getInstance()
        backupManager  = BackupManager(
            db = firestore,
            productDao = dao,
            cloudinaryService = cloudinary,
            context = this
        )


        val imageFile = File("/path/to/image.jpg") // Ganti dengan file sebenarnya
        productViewModel.uploadImageToCloudinary(imageFile)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuanTheme {
                MainScreen(transactionViewModel, productViewModel, bookViewModel, backupManager)
            }
        }
    }
}