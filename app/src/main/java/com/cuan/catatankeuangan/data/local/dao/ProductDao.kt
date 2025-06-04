package com.cuan.catatankeuangan.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cuan.catatankeuangan.data.local.entities.Category
import com.cuan.catatankeuangan.data.local.entities.CategoryWithProducts
import com.cuan.catatankeuangan.data.local.entities.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product_table ORDER BY name")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table ORDER BY name")
    suspend fun getAllProductsForBackup(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

//    @Query("SELECT * FROM product_table WHERE categoryId = :category")
//    fun getProductsByCategory(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(category: Category): Long

    // Checks if there were already existing names when renaming
    @Query("SELECT COUNT(*) FROM category_table WHERE name = :categoryName")
    suspend fun countCategoryByName(categoryName: String): Int

    /*
    Checks if there were already existing names when renaming
    While also making sure the new name is not the same with the previous name
    */
    @Query("SELECT COUNT(*) FROM category_table WHERE name = :newName AND id != :categoryId")
    suspend fun countCategoryNameExists(categoryId: Int, newName: String): Int

    @Update
    suspend fun updateCategoryName(category: Category)

    @Query("SELECT * FROM category_table ORDER BY name")
    fun getAllCategories(): LiveData<List<Category>>

    @Transaction
    @Query("SELECT * FROM category_table ORDER BY name")
    fun getAllCategoryWithProducts(): LiveData<List<CategoryWithProducts>>

    @Query("UPDATE product_table SET categoryId = :categoryId WHERE id = :productId")
    suspend fun updateProductCategory(productId: Int, categoryId: Int?)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category_table ORDER BY name")
    suspend fun getAllCategoriesForBackup(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<Category>)


}