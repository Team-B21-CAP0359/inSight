package com.bangkit.capstone.insightapp.view.activity.welcomescreen.forumkm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.databinding.ActivityUmkmWelcome1Binding
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

        val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
        val pref2 = applicationContext.getSharedPreferences("data_finish", MODE_PRIVATE)
        val editor = pref.edit()
        val editorSave = pref2.edit()

        refUser = FirebaseDatabase.getInstance().reference.child("Umkm")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        val umkm = "umkm"
        userHashMap["uid"] = mAuth.currentUser?.uid.toString()
        userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
        userHashMap["username"] = mAuth.currentUser?.displayName.toString()
        userHashMap["email"] = mAuth.currentUser?.email.toString()
        userHashMap["status"] = "logged"
        userHashMap["search"] = binding.namaPerusahaan.text.toString().toLowerCase(Locale.ROOT)
        userHashMap["nama_perusahaan"] = binding.namaPerusahaan.text.toString()
        userHashMap["alamat_perusahaan"] = binding.alamatPerusahaan.text.toString()
        userHashMap["nomer_wa"] = binding.noWhatsapp.text.toString()
        editor.putString("role", umkm)
        editor.putString("nama_perusahaan", binding.namaPerusahaan.text.toString())
        editor.putString("alamat_perusahaan", binding.alamatPerusahaan.text.toString())
        editor.putString("nomer_wa", binding.noWhatsapp.text.toString())
        editor.apply()
        editorSave.putBoolean("finish", true).apply()
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