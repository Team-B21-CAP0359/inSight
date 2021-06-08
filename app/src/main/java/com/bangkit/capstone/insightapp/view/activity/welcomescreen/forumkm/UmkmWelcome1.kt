package com.bangkit.capstone.insightapp.view.activity.welcomescreen.forumkm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityUmkmWelcome1Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcomeBinding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class UmkmWelcome1 : AppCompatActivity() {

    private var _binding: ActivityUmkmWelcome1Binding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityUmkmWelcome1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        binding.nextUmkm.setOnClickListener {
            uplaodFile()
        }

    }

    private fun uplaodFile() {
        refUser = FirebaseDatabase.getInstance().reference.child("Umkm")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["uid"] = mAuth.currentUser?.uid.toString()
        userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
        userHashMap["username"] = mAuth.currentUser?.displayName.toString()
        userHashMap["email"] = mAuth.currentUser?.email.toString()
        userHashMap["status"] = "logged"
        userHashMap["search"] = mAuth.currentUser?.displayName.toString().toLowerCase(Locale.ROOT)
        userHashMap["nama_perusahaan"] = binding.namaPerusahaan.text.toString()
        userHashMap["alamat_perusahaan"] = binding.alamatPerusahaan.text.toString()
        userHashMap["nomer_wa"] = binding.noWhatsapp.text.toString()
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }
}