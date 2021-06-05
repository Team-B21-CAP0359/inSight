package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityLoginBinding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private var _binding: ActivityWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

    }
}