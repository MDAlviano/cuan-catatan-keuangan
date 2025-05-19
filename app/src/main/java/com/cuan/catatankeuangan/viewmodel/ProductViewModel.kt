package com.cuan.catatankeuangan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.CategoryWithProducts
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
    val allCategoryWithProducts: LiveData<List<CategoryWithProducts>> =
        repository.getAllCategoryWithProducts()

    fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
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

    val _categoryNameConflict = MutableLiveData<Boolean>()
    val categoryNameConflict: LiveData<Boolean> = _categoryNameConflict

    fun addCategory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.doesCategoryNameExists(name)) {
                _categoryNameConflict.postValue(true)
            } else {
                repository.addCategory(Category(name = name))
                _categoryNameConflict.postValue(false)
            }
        }
    }

    fun addProductToCategory(productId: Int, categoryId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProductCategory(productId, categoryId)
        }
    }

    fun removeProductFromCategory(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProductCategory(productId, null)
        }
    }

    private val _renameConflict = MutableLiveData<Boolean>()
    val renameConflict: LiveData<Boolean> = _renameConflict

    fun renameCategory(category: CategoryWithProducts, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.doesRenamedCategoryNameExist(category.category.id, newName)) {
                _renameConflict.postValue(true)
            } else {
                repository.updateCategoryName(category.category.copy(name = newName))
                _renameConflict.postValue(false)
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(category)
        }
    }

}