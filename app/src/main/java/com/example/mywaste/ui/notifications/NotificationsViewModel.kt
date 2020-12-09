package com.example.mywaste.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь будет раздел для аудитора"
    }
    val text: LiveData<String> = _text
}