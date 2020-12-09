package com.example.mywaste.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Waste::class, Category::class), version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wasteDao(): WasteDAO
}