package com.bangkit.capstone.insightapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bangkit.capstone.insightapp.MainActivity
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivitySplashBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Glide.with(this)
            .load(R.drawable.splash_logo)
            .into(binding.SplashView)

        Handler(Looper.getMainLooper()).postDelayed({
            if (null != user) {
                val menuIntent = Intent(this, MenuActivity::class.java)
                startActivity(menuIntent)
            } else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }, 3000)

    }
}