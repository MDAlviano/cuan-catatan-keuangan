package com.cuan.catatankeuangan.presentation.utils

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Utility for understand how to convert between LocalDate (in your Kotlin code) and the corresponding data type in your SQLite database (likely Long for epoch milliseconds or String for ISO 8601 format).
 *
 * @author m
 */
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }
    }

    @TypeConverter
    fun localDateToTimestamp(date: LocalDate?): Long? {
        return date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }
}