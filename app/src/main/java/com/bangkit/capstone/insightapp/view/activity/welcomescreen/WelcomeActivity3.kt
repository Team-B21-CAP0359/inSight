package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome2Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome3Binding

class WelcomeActivity3 : AppCompatActivity() {

    private var _binding: ActivityWelcome3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome3Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}