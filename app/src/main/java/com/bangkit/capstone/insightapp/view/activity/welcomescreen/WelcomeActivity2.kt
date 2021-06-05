package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome2Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcomeBinding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity

class WelcomeActivity2 : AppCompatActivity() {

    private var _binding: ActivityWelcome2Binding? = null
    private val binding get() = _binding!!

    private var jenisShirtValid = false
    private var namaShirtValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.next2.setOnClickListener {
            val intent = Intent(this, WelcomeActivity3::class.java)
            startActivity(intent)
        }

    }
}