package com.cuan.catatankeuangan.data.repository

import android.content.Context
import androidx.work.*
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.repository.BackupManager
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class AutoBackupWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val db = MainDatabase.getInstance(applicationContext)
            val backupManager = BackupManager(
                db = FirebaseFirestore.getInstance(),
                productDao = db.productDao(),
                transactionDao = db.transactionDao(),
                context = applicationContext
            )

            val email = inputData.getString("userEmail") ?: return Result.failure()
            backupManager.backupAll(email)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

