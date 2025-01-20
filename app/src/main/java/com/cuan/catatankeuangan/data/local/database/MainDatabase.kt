package com.cuan.catatankeuangan.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cuan.catatankeuangan.data.local.dao.ProductDao
import com.cuan.catatankeuangan.data.local.dao.TransactionDao
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.Transaction

@Database(
    entities = [Transaction::class, Product::class],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getMainDatabase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
