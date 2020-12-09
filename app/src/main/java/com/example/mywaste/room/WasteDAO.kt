package com.example.mywaste.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import java.util.Calendar

@Dao
interface WasteDAO {
    @Query("SELECT * FROM waste")
    fun getAllWaste(): List<Waste>

    @Query("SELECT * FROM category")
    fun getAllCategory(): List<Category>

    @Query("SELECT * FROM category where nameCategory = :name")
    fun getCategoryByName(name: String): Category?

    @Query("SELECT * FROM category where cid = :id")
    fun getCategoryById(id: Int): Category?

/*    @Transaction
    @Query("SELECT * FROM category")
    fun getWasteWithCategory(): List<WasteWithCategoryLists>*/

    @Insert
    fun insertCategory(category: Category): Long

    @Insert
    fun insertWaste(waste: Waste)

    @Query("SELECT * FROM WASTE WHERE date BETWEEN :start AND :end")
    fun getWasteByDate(start: Calendar, end: Calendar): List<Waste>

/*    fun getByDate(start: Calendar, end: Calendar) =
        getWasteWithCategory().map {
            it.copy(categorylists = it.categorylists.filter { waste ->
                waste.date.after(start) && waste.date.before(
                    end
                )
            })
        }*/
}