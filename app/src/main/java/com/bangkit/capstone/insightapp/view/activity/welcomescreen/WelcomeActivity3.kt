package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome2Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome3Binding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity

class WelcomeActivity3 : AppCompatActivity() {

    private var _binding: ActivityWelcome3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.next3.setOnClickListener {
            val intent = Intent(this, WelcomeActivity4::class.java)
            startActivity(intent)
        }
    }
}