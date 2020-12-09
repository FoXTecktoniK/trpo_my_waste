package com.example.mywaste.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mywaste.domain.WasteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class ReportViewModel : ViewModel() {
    private val _itemsLiveData = MutableLiveData<List<WasteModel>>();
    val itemsLiveData: LiveData<List<WasteModel>>
        get() = _itemsLiveData
    var startDate: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())
    var endDate: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _itemsLiveData.postValue(WasteModel.getAll())
            }
        }
    }

    fun updateInfo() {
        viewModelScope.launch {
            _itemsLiveData.value = WasteModel.getByDate(startDate.value!!, endDate.value!!)
        }
    }
}