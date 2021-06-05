package com.bangkit.capstone.insightapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityDetailUserBinding
import com.bangkit.capstone.insightapp.databinding.ContentScrollingBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class DetailUserActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!

    private var _binding2: ContentScrollingBinding? = null
    private val bindingDetail get() = _binding2!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _binding2 = binding.content

        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<ImageView>(R.id.iv_detail_image).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        bindingDetail.etNameUser.text = currentUser?.displayName
        bindingDetail.etEmailUser.text = currentUser?.email

        Glide.with(this)
            .load(currentUser?.photoUrl)
            .into(binding.ivDetailImage)


        bindingDetail.buttonLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            mAuth.signOut()
            val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
            val pref2 = applicationContext.getSharedPreferences("data_finish", MODE_PRIVATE)
            pref.edit().clear().apply()
            pref2.edit().clear().apply()
            startActivity(intent)
            finish()

        }


    }
}