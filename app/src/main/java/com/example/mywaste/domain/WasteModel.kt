package com.example.mywaste.domain

import com.example.mywaste.MyApplication
import com.example.mywaste.room.Waste
import java.util.Calendar
import java.util.GregorianCalendar

data class WasteModel(
    val categoryName: String,
    val shop: String,
    val total: Float,
    val currency: String,
    val date: GregorianCalendar
) {
    private val months = arrayOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
        "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )

    fun getMonthName(): String {
        return months[date.get(Calendar.MONTH)]
    }

    private val currencies = mapOf("Доллар" to 60, "Рубль" to 1, "Евро" to 70)
    val totalRUR = total * currencies.getOrDefault(currency, 1)

    fun insert() {
        MyApplication.getInstance().appDatabase.wasteDao()
            .insertWaste(Waste(0, shop, total, currency, date, CategoryModel.getId(categoryName), totalRUR))
    }

    companion object {
        fun getAll(): List<WasteModel> =
            MyApplication.getInstance().appDatabase.wasteDao().getAllWaste().map(::createFromData)

        fun createFromData(dataModel: Waste): WasteModel = WasteModel(
            CategoryModel.getById(dataModel.categoryId)!!.name,
            dataModel.shop,
            dataModel.total,
            dataModel.currency,
            dataModel.date
        )

        fun getByDate(start: Calendar, end: Calendar): List<WasteModel> =
            MyApplication.getInstance().appDatabase.wasteDao().getWasteByDate(start, end).map(::createFromData)
    }
}