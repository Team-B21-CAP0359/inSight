package com.bangkit.capstone.insightapp.view.activity.ui.shoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Sepatu"
    }
    val text: LiveData<String> = _text
}