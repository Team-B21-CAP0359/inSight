package com.bangkit.capstone.insightapp.view.activity.ui.tshirt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pakaian"
    }
    val text: LiveData<String> = _text
}