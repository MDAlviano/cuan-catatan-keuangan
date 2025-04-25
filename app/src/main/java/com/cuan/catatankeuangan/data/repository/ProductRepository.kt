package com.cuan.catatankeuangan.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.cuan.catatankeuangan.data.local.dao.ProductDao
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.Product

class ProductRepository(context: Context) {
    private val productDao = MainDatabase.getMainDatabase(context).productDao()

    fun getAllProducts() = productDao.getAllProducts()

    suspend fun addProduct(product: Product) {
        productDao.addProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    fun getAllCategories() = productDao.getAllCategories()
    suspend fun addCategory(category: Category): Long = productDao.addCategory(category)

}