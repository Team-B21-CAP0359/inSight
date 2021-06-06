package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome5Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class WelcomeActivity5 : AppCompatActivity() {

    private var _binding: ActivityWelcome5Binding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        _binding = ActivityWelcome5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference
        binding.next5.isEnabled = false

        binding.upload.setOnClickListener {

            selectImage()

        }

        binding.next5.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Menupload File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fileName = mAuth.currentUser?.uid
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName-pants")

        storageReference.putFile(imageUri).addOnSuccessListener {
            binding.previewPhoto.setImageURI(null)
            if (progressDialog.isShowing) progressDialog.dismiss()
            moveIntent()
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
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
            binding.next5.isEnabled = true
            binding.previewPhoto.visibility = View.VISIBLE
            binding.previewPhoto.setImageURI(imageUri)
        }
    }

    private fun moveIntent() {
        val intent = Intent(this, WelcomeActivity6::class.java)
        startActivity(intent)
    }
}