package com.example.mywaste.domain

import com.example.mywaste.MyApplication
import com.example.mywaste.room.Category

class CategoryModel(
    nameCategory: String,
    val descriptionCategory: String
) {

    public val name = nameCategory.toLowerCase().capitalize()

    companion object {
        fun createFromData(category: Category): CategoryModel {
            return CategoryModel(category.nameCategory, category.descriptionCategory.orEmpty())
        }

        fun getByName(name: String): CategoryModel? {
            return MyApplication.getInstance().appDatabase.wasteDao().getCategoryByName(name)?.let(::createFromData)
        }

        fun getDataByName(name: String): Category?{
            return MyApplication.getInstance().appDatabase.wasteDao().getCategoryByName(name)
        }

        fun getById(id: Int): CategoryModel? {
            return MyApplication.getInstance().appDatabase.wasteDao().getCategoryById(id)?.let(::createFromData)
        }

        fun getAll(): List<CategoryModel> {
            return MyApplication.getInstance().appDatabase.wasteDao().getAllCategory().map(::createFromData)
        }

        fun getId(name: String): Int {
            val formattedName = name.toLowerCase().capitalize()
            return getDataByName(formattedName)?.cid ?: MyApplication.getInstance().appDatabase.wasteDao()
                .insertCategory(Category(0, formattedName, null)).toInt()
        }
    }
}