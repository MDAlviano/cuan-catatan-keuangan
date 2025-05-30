package com.cuan.catatankeuangan.data.local.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cuan.catatankeuangan.data.local.dao.BookDao
import com.cuan.catatankeuangan.data.local.dao.ProductDao
import com.cuan.catatankeuangan.data.local.dao.TransactionDao
import com.cuan.catatankeuangan.data.local.entities.Book
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.local.entities.Transaction

@Database(
    entities = [Book::class, Transaction::class, Product::class, Category::class],
    version = 1,
    exportSchema = false,
//    autoMigrations = [
//        AutoMigration(
//            from = 1,
//            to = 2,
//        )
//    ]
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
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
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun getInstance(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
