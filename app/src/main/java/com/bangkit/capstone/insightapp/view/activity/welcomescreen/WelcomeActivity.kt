package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityLoginBinding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcomeBinding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity

class WelcomeActivity : AppCompatActivity() {

    private var _binding: ActivityWelcomeBinding? = null
    private val binding get() = _binding!!

    private var jenisShirtValid = false
    private var namaShirtValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        validateButton()

        binding.jenisStyle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                validateJenisShirt()
            }
        })

        binding.namaPakaian.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                validateTextNamaShirt()
            }

        })

        binding.next1.setOnClickListener {
            val intent = Intent(this, WelcomeActivity2::class.java)
            val namaShirtValue = binding.namaPakaian.text.toString()
            val jenisShirtValue = binding.namaPakaian.text.toString()
            val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("nama_shirt", namaShirtValue)
            editor.putString("jenis_shirt", jenisShirtValue)
            editor.apply()
            startActivity(intent)
        }

    }

    private fun validateTextNamaShirt() {
        val input = binding.namaPakaian.text.toString()
        if (input.isEmpty()) {
            namaShirtValid = false
            showNamaShirtAlert(true)
        } else {
            namaShirtValid = true
            showNamaShirtAlert(false)
        }
        validateButton()
    }


    private fun validateJenisShirt() {
     val input = binding.jenisStyle.text.toString()
        if (input.isEmpty()) {
            jenisShirtValid = false
            showJenisShirtAlert(true)
        } else {
            jenisShirtValid = true
            showJenisShirtAlert(false)
        }
        validateButton()
    }

    private fun showJenisShirtAlert(isNotValid: Boolean) {
        binding.jenisStyle.error = if (isNotValid) getString(R.string.not_valid_empty) else null
    }

    private fun showNamaShirtAlert(isNotValid: Boolean) {
        binding.namaPakaian.error = if (isNotValid) getString(R.string.not_valid_empty) else null
    }

    private fun validateButton() {
        if (namaShirtValid && jenisShirtValid) {
            binding.next1.isEnabled = true
            binding.next1.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light))
        } else {
            binding.next1.isEnabled = false
            binding.next1.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }


}