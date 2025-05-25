package com.cuan.catatankeuangan.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val type: BookType,
    val description: String?,
    val address: String?,
    val phone: Long?
) : Parcelable

enum class BookType {
    BISNIS, PRIBADI
}
