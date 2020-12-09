package com.example.mywaste.room

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): GregorianCalendar? {
        return value?.let { return GregorianCalendar().apply { time = Date(it) } }
    }

    @TypeConverter
    fun dateToTimestamp(date: Calendar?): Long? {
        return date?.time?.time
    }
}
