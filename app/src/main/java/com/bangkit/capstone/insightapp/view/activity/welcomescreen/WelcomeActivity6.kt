package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome5Binding
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome6Binding
import com.bangkit.capstone.insightapp.view.activity.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.HashMap

class WelcomeActivity6 : AppCompatActivity() {

    private var _binding: ActivityWelcome6Binding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference
        binding.finish.isEnabled = false

        binding.upload.setOnClickListener {

            selectImage()

        }

        binding.finish.setOnClickListener {

            uploadImage()

        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.finish.isEnabled = true
            binding.previewPhoto.visibility = View.VISIBLE
            binding.previewPhoto.setImageURI(imageUri)
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Menupload File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fileName = mAuth.currentUser?.uid
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName-shoe")

        storageReference.putFile(imageUri).addOnSuccessListener {
            binding.previewPhoto.setImageURI(null)
            if (progressDialog.isShowing) progressDialog.dismiss()
            moveIntent()
            uploadFile()
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }

    private fun uploadFile() {
        val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
        refUser = FirebaseDatabase.getInstance().reference.child("Users")
            .child(mAuth.currentUser?.uid.toString())
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
    }

    private fun moveIntent() {
        val intent = Intent(this, MenuActivity::class.java)
        val pref2 = applicationContext.getSharedPreferences("data_finish", MODE_PRIVATE)
        val editor = pref2.edit()
        editor.putBoolean("finish", true).apply()
        startActivity(intent)
        finish()
    }
}