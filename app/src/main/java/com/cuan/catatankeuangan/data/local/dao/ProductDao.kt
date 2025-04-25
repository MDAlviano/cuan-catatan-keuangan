package com.cuan.catatankeuangan.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getAllProducts(): LiveData<List<Product>>

//    @Query("SELECT * FROM product_table WHERE categoryId = :category")
//    fun getProductsByCategory(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(category: Category): Long

    @Query("SELECT * FROM category_table ORDER BY name")
    fun getAllCategories(): LiveData<List<Category>>

}