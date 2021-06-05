package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome3Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome4Binding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class WelcomeActivity4 : AppCompatActivity() {

    private var _binding: ActivityWelcome4Binding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        binding.finish.setOnClickListener {
            val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
            refUser = FirebaseDatabase.getInstance().reference.child("Users").child(mAuth.currentUser?.uid.toString())
            val userHashMap = HashMap<String, Any>()
            userHashMap["uid"] = mAuth.currentUser?.uid.toString()
            userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
            userHashMap["username"] = mAuth.currentUser?.displayName.toString()
            userHashMap["email"] = mAuth.currentUser?.email.toString()
            userHashMap["status"] = "offline"
            userHashMap["search"] = mAuth.currentUser?.displayName.toString().toLowerCase(Locale.ROOT)
            userHashMap["bio"] = "Empty"
            userHashMap["jenis_celana"] = pref.getString("jenis_celana", null).toString()
            userHashMap["jenis_sepatu"] = pref.getString("jenis_sepatu", null).toString()
            userHashMap["jenis_shirt"] = pref.getString("jenis_shirt", null).toString()
            userHashMap["nama_celana"] = pref.getString("nama_celana", null).toString()
            userHashMap["nama_sepatu"] = pref.getString("nama_sepatu", null).toString()
            userHashMap["nama_shirt"] = pref.getString("nama_shirt", null).toString()

            refUser.updateChildren(userHashMap)
                    .addOnCompleteListener { tasks ->
                        if (tasks.isSuccessful) {
                            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                        }
                    }

            val intent = Intent(this, MenuActivity::class.java)
            val pref2 = applicationContext.getSharedPreferences("data_finish", MODE_PRIVATE)
            val editor = pref2.edit()
            editor.putBoolean("finish", true).apply()
            startActivity(intent)
            finish()
        }
    }
}