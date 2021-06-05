package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome3Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome4Binding

class WelcomeActivity4 : AppCompatActivity() {

    private var _binding: ActivityWelcome4Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome4Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}