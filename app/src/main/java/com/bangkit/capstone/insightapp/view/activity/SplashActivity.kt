package com.bangkit.capstone.insightapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivitySplashBinding
import com.bangkit.capstone.insightapp.sharedpref.NightModeSharedPref
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var sharedPref: NightModeSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = applicationContext.getSharedPreferences("data_finish", MODE_PRIVATE)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Glide.with(this)
            .load(R.drawable.splash_logo)
            .into(binding.SplashView)

        isNightMode()

        Handler(Looper.getMainLooper()).postDelayed({
            if (null != user && pref.getBoolean("finish", false)) {
                val menuIntent = Intent(this, MenuActivity::class.java)
                startActivity(menuIntent)
                finish()
            } else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }, 3000)

    }

    private fun isNightMode() {
        sharedPref = NightModeSharedPref(this)
        if (sharedPref.loadNightModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}