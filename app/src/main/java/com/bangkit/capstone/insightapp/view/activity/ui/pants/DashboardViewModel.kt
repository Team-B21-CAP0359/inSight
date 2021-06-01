package com.bangkit.capstone.insightapp.view.activity.ui.pants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Celana"
    }
    val text: LiveData<String> = _text
}