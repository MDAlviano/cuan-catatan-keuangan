package com.cuan.catatankeuangan.data.repository

import android.content.Context
import com.cuan.catatankeuangan.data.local.database.MainDatabase
import com.cuan.catatankeuangan.data.local.entities.Book

class BookRepository(context: Context) {
    private val bookDao = MainDatabase.getMainDatabase(context).bookDao()

    fun getAllBooks() = bookDao.getAllBooks()

    suspend fun addBook(book: Book) {
        bookDao.addBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
}
