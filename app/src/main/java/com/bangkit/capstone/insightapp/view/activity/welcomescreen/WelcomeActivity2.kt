package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome2Binding

class WelcomeActivity2 : AppCompatActivity() {

    private var _binding: ActivityWelcome2Binding? = null
    private val binding get() = _binding!!

    private var jenisCelanaValid = false
    private var namaCelanaValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.jenisStyleCelana.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                validateStyleCelana()
            }

        })

        binding.namaCelana.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                validateNamaCelana()
            }
        })

        binding.next2.setOnClickListener {
            val intent = Intent(this, WelcomeActivity3::class.java)
            val namaCelanaValue = binding.namaCelana.text.toString()
            val jenisCelanaValue = binding.jenisStyleCelana.text.toString()
            val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("nama_celana", namaCelanaValue)
            editor.putString("jenis_celana", jenisCelanaValue)
            editor.apply()
            startActivity(intent)
        }

    }

    private fun validateNamaCelana() {
        val input = binding.namaCelana.text.toString()
        if (input.isEmpty()) {
            namaCelanaValid = false
            showNamaCelanaAlert(true)
        } else {
            namaCelanaValid = true
            showNamaCelanaAlert(false)
        }
        validateButton()
    }

    private fun validateStyleCelana() {
        val input = binding.jenisStyleCelana.text.toString()
        if (input.isEmpty()) {
            jenisCelanaValid = false
            showJenisCelanaAlert(true)
        } else {
            jenisCelanaValid = true
            showJenisCelanaAlert(false)
        }
        validateButton()
    }

    private fun showJenisCelanaAlert(isNotValid: Boolean) {
        binding.jenisStyleCelana.error =
            if (isNotValid) getString(R.string.not_valid_empty) else null
    }

    private fun showNamaCelanaAlert(isNotValid: Boolean) {
        binding.namaCelana.error = if (isNotValid) getString(R.string.not_valid_empty) else null
    }

    private fun validateButton() {
        if (namaCelanaValid && jenisCelanaValid) {
            binding.next2.isEnabled = true
            binding.next2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_blue_light
                )
            )
        } else {
            binding.next2.isEnabled = false
            binding.next2.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.darker_gray
                )
            )
        }
    }
}