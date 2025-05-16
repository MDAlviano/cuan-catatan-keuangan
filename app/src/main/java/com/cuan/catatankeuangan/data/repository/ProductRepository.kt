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

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    fun getAllCategories() = productDao.getAllCategories()

    fun getAllCategoryWithProducts() = productDao.getAllCategoryWithProducts()

    suspend fun addCategory(category: Category): Long = productDao.addCategory(category)

    suspend fun updateCategoryName(category: Category) {
        productDao.updateCategoryName(category)
    }

    suspend fun doesCategoryNameExists(name: String): Boolean {
        return productDao.countCategoryByName(name) > 0
    }

    suspend fun doesRenamedCategoryNameExist(id: Int, name: String): Boolean {
        return productDao.countCategoryNameExists(id, name) > 0
    }

    suspend fun updateProductCategory(productId: Int, categoryId: Int?) {
        productDao.updateProductCategory(productId, categoryId)
    }

    suspend fun deleteCategory(category: Category) {
        productDao.deleteCategory(category)
    }

}