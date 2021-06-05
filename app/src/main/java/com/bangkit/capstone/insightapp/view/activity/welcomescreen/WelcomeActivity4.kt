package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome3Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome4Binding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity

class WelcomeActivity4 : AppCompatActivity() {

    private var _binding: ActivityWelcome4Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.finish.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}