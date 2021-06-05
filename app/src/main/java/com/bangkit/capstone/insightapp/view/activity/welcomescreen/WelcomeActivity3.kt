package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome2Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome3Binding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity

class WelcomeActivity3 : AppCompatActivity() {

    private var _binding: ActivityWelcome3Binding? = null
    private val binding get() = _binding!!

    private var jenisSepatuValid = false
    private var namaSepatuValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.namaSepatu.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                validateNamaSepatu()
            }

        })

        binding.jenisStyleSepatu.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                validateStyleSepatu()
            }

        })

        binding.next3.setOnClickListener {
            val intent = Intent(this, WelcomeActivity4::class.java)
            val namaSepatuValue = binding.namaSepatu.text.toString()
            val jenisSepatuValue = binding.jenisStyleSepatu.text.toString()
            val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("nama_sepatu", namaSepatuValue)
            editor.putString("jenis_sepatu", jenisSepatuValue)
            editor.apply()
            startActivity(intent)
        }
    }

    private fun validateStyleSepatu() {
        val input = binding.jenisStyleSepatu.text.toString()
        if (input.isEmpty()) {
            namaSepatuValid = false
            showStyleSepatuAlert(true)
        } else {
            namaSepatuValid = true
            showStyleSepatuAlert(false)
        }
        validateButton()
    }

    private fun validateNamaSepatu() {
        val input = binding.namaSepatu.text.toString()
        if (input.isEmpty()) {
            jenisSepatuValid = false
            showJenisSepatuAlert(true)
        } else {
            jenisSepatuValid = true
            showJenisSepatuAlert(false)
        }
        validateButton()
    }

    private fun showJenisSepatuAlert(isNotValid: Boolean) {
        binding.jenisStyleSepatu.error = if (isNotValid) getString(R.string.not_valid_empty) else null
    }

    private fun showStyleSepatuAlert(isNotValid: Boolean) {
        binding.namaSepatu.error = if (isNotValid) getString(R.string.not_valid_empty) else null
    }

    private fun validateButton() {
        if (namaSepatuValid && jenisSepatuValid) {
            binding.next3.isEnabled = true
            binding.next3.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light))
        } else {
            binding.next3.isEnabled = false
            binding.next3.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

}