@file:Suppress("DEPRECATION")

package com.bangkit.capstone.insightapp.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.databinding.ActivityAddBarangBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

@Suppress("DEPRECATION")
class AddBarangActivity : AppCompatActivity() {

    private var _binding: ActivityAddBarangBinding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        binding.nextUmkm.setOnClickListener {
            uploadImage()
        }

        binding.upload.setOnClickListener {
            selectImage()
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
            binding.nextUmkm.isEnabled = true
            binding.priviewImage.visibility = View.VISIBLE
            binding.priviewImage.setImageURI(imageUri)
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Menupload File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fileName = mAuth.currentUser?.uid
        val storageReference =
            FirebaseStorage.getInstance().getReference("images/$fileName-shop/barang")

        storageReference.putFile(imageUri).addOnSuccessListener {
            binding.priviewImage.setImageURI(null)
            if (progressDialog.isShowing) progressDialog.dismiss()
            uploadData()
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }

    private fun uploadData() {
        val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
        refUser = FirebaseDatabase.getInstance().reference.child("barangJual")
            .child(mAuth.currentUser?.uid.toString())

        val COUNTRY = "ID"
        val LANGUAGE = "in"
        val harga = binding.hargaJual.text.toString().toDouble()

        val userHashMap = HashMap<String, Any>()
        userHashMap["uid"] = mAuth.currentUser?.uid.toString()
        userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
        userHashMap["username"] = mAuth.currentUser?.displayName.toString()
        userHashMap["email"] = mAuth.currentUser?.email.toString()
        userHashMap["search"] = mAuth.currentUser?.displayName.toString().toLowerCase(Locale.ROOT)
        userHashMap["nama_barang"] = binding.barangJual.text.toString()
        userHashMap["deskripsi_barang"] = binding.deksirpsiJual.text.toString()
        userHashMap["nama_perusahaan"] = pref.getString("nama_perusahaan", null).toString()
        userHashMap["alamat_perusahaan"] = pref.getString("alamat_perusahaan", null).toString()
        userHashMap["wa_penjual"] = pref.getString("nomer_wa", null).toString()
        userHashMap["harga_barang"] =
            NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(harga)
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    moveIntent()
                    Toast.makeText(this, "Jual Barang Sukses !", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun moveIntent() {
        val intent = Intent(this, DetailUserActivity::class.java)
        startActivity(intent)
        finish()
    }
}