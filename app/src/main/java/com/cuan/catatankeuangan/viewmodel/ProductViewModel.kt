package com.cuan.catatankeuangan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.Product
import com.cuan.catatankeuangan.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository = ProductRepository(application)

    private var recentlyDeletedProduct: Product? = null

    val allProducts: LiveData<List<Product>> = repository.getAllProducts()
    val allCategories: LiveData<List<Category>> = repository.getAllCategories()

    fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product)
        }
    }

    fun deleteProduct(product: Product, onUndoTimeout: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            recentlyDeletedProduct = product
            repository.deleteProduct(product) // delete from DB

            delay(5000) // undo countdown
            if (recentlyDeletedProduct == product) {
                onUndoTimeout()
                recentlyDeletedProduct = null
            }
        }
    }

    fun undoDeleteProduct() {
        recentlyDeletedProduct?.let {
            addProduct(it)
            recentlyDeletedProduct = null
        }
    }

//    fun deleteProduct(product: Product) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteProduct(product)
//        }
//    }

    fun addCategory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(Category(name = name))
        }
    }

}