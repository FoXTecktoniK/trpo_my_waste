package com.example.mywaste.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.GregorianCalendar

@Entity
data class Waste(
    @PrimaryKey(autoGenerate = true) val wid: Int,
    val shop: String,
    val total: Float,
    val currency: String,
    val date: GregorianCalendar,
    val categoryId: Int,
    val totalRUR: Float
) {
}

@Entity(
    indices = [Index(
        value = arrayOf("nameCategory"),
        unique = true
    )]
)
data class Category(
    @PrimaryKey(autoGenerate = true) val cid: Int,
    val nameCategory: String,
    val descriptionCategory: String?
)

data class WasteWithCategoryLists(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "cid",
        entityColumn = "categoryId"
    )
    val categorylists: List<Waste>
)
