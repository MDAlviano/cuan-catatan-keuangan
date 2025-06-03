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
import androidx.work.*
import com.cuan.catatankeuangan.data.repository.AutoBackupWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private val transactionViewModel: TransactionViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private val bookViewModel: BookViewModel by viewModels()

    private lateinit var backupManager: BackupManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Backup
        val dbInstance = MainDatabase.getInstance(this)
        val productDao = dbInstance.productDao()
        val transactionDao = dbInstance.transactionDao()
        val cloudinary = CloudinaryService(context = this)
        val firestore = FirebaseFirestore.getInstance()
        backupManager = BackupManager(
            db = firestore,
            productDao = productDao,
            transactionDao = transactionDao,
            cloudinaryService = cloudinary,
            context = this
        )
        // Jadwalkan auto backup
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val email = prefs.getString("userEmail", null)
        if (!email.isNullOrEmpty()) {
            scheduleAutoBackup(email)
        }

        val imageFile = File("/path/to/image.jpg")
        productViewModel.uploadImageToCloudinary(imageFile)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuanTheme {
                MainScreen(transactionViewModel, productViewModel, bookViewModel, backupManager)
            }
        }
    }

    private fun scheduleAutoBackup(email: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val inputData = Data.Builder()
            .putString("userEmail", email)
            .build()

        val request = PeriodicWorkRequestBuilder<AutoBackupWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_backup",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

}