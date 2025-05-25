package com.cuan.catatankeuangan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cuan.catatankeuangan.data.local.entities.Book
import com.cuan.catatankeuangan.data.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository = BookRepository(application)

    val allBooks: LiveData<List<Book>> = repository.getAllBooks()

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
    }

}