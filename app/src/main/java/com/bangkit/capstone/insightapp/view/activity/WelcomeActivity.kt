package com.bangkit.capstone.insightapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()

    }
}