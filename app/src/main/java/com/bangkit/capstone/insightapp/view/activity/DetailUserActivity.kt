package com.bangkit.capstone.insightapp.view.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityDetailUserBinding
import com.bangkit.capstone.insightapp.databinding.ContentScrollingBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File

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
        bindingDetail.loadImage.visibility = View.VISIBLE

        bindingDetail.showShirtStyle.setOnClickListener {
            bindingDetail.styleShirt.visibility = View.VISIBLE
            bindingDetail.nameShirt.visibility = View.VISIBLE
            bindingDetail.closeShirtStyle.visibility = View.VISIBLE
            bindingDetail.showShirtStyle.visibility = View.GONE
        }

        bindingDetail.closeShirtStyle.setOnClickListener {
            bindingDetail.styleShirt.visibility = View.GONE
            bindingDetail.nameShirt.visibility = View.GONE
            bindingDetail.closeShirtStyle.visibility = View.GONE
            bindingDetail.showShirtStyle.visibility = View.VISIBLE
        }

        bindingDetail.showPantStyle.setOnClickListener {
            bindingDetail.stylePants.visibility = View.VISIBLE
            bindingDetail.namePants.visibility = View.VISIBLE
            bindingDetail.closePantStyle.visibility = View.VISIBLE
            bindingDetail.showPantStyle.visibility = View.GONE
        }

        bindingDetail.closePantStyle.setOnClickListener {
            bindingDetail.stylePants.visibility = View.GONE
            bindingDetail.namePants.visibility = View.GONE
            bindingDetail.closePantStyle.visibility = View.GONE
            bindingDetail.showPantStyle.visibility = View.VISIBLE
        }

        bindingDetail.showShoeStyle.setOnClickListener {
            bindingDetail.styleShoe.visibility = View.VISIBLE
            bindingDetail.nameShoe.visibility = View.VISIBLE
            bindingDetail.closeShoeStyle.visibility = View.VISIBLE
            bindingDetail.showShoeStyle.visibility = View.GONE
        }

        bindingDetail.closeShoeStyle.setOnClickListener {
            bindingDetail.styleShoe.visibility = View.GONE
            bindingDetail.nameShoe.visibility = View.GONE
            bindingDetail.closeShoeStyle.visibility = View.GONE
            bindingDetail.showShoeStyle.visibility = View.VISIBLE
        }

        loadImage()
        loadUSer()

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

    private fun loadImage() {
        val imageShirt = mAuth.currentUser?.uid
        val storageRefShirt =
            FirebaseStorage.getInstance().reference.child("images/$imageShirt-shirt")
        val storageRefPants =
            FirebaseStorage.getInstance().reference.child("images/$imageShirt-pants")
        val storageRefShoe =
            FirebaseStorage.getInstance().reference.child("images/$imageShirt-shoe")

        val localFile = File.createTempFile("tempImage", "jpg")
        val localFile2 = File.createTempFile("tempImage", "jpg")
        val localFile3 = File.createTempFile("tempImage", "jpg")
        storageRefShirt.getFile(localFile).addOnSuccessListener {
            bindingDetail.loadImage.visibility = View.GONE
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            bindingDetail.fashionUserShirt.setImageBitmap(bitmap)
        }.addOnFailureListener {
            bindingDetail.loadImage.visibility = View.GONE
            Toast.makeText(this, "Failed to receive shirt image", Toast.LENGTH_SHORT).show()
        }

        storageRefPants.getFile(localFile2).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile2.absolutePath)
            bindingDetail.fashionUserPants.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to receive pants image", Toast.LENGTH_SHORT).show()
        }

        storageRefShoe.getFile(localFile3).addOnSuccessListener {
            val bitMap = BitmapFactory.decodeFile(localFile3.absolutePath)
            bindingDetail.fashionUserShoe.setImageBitmap(bitMap)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to receive shoe image", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadUSer() {

        val currentUser = mAuth.currentUser
        bindingDetail.etNameUser.text = currentUser?.displayName
        bindingDetail.etEmailUser.text = currentUser?.email

        Glide.with(this)
            .load(currentUser?.photoUrl)
            .into(binding.ivDetailImage)

        val pref = applicationContext.getSharedPreferences("data", MODE_PRIVATE)
        bindingDetail.styleCelana.text = pref.getString("jenis_celana", null).toString()
        bindingDetail.styleSepatu.text = pref.getString("jenis_sepatu", null).toString()
        bindingDetail.styleBaju.text = pref.getString("jenis_shirt", null).toString()
        bindingDetail.namaCelana.text = pref.getString("nama_celana", null).toString()
        bindingDetail.namaSepatu.text = pref.getString("nama_sepatu", null).toString()
        bindingDetail.namaBaju.text = pref.getString("nama_shirt", null).toString()
    }

}

//animation file use
//https://lottiefiles.com/nasomatrix
//https://lottiefiles.com/jocars5
//https://lottiefiles.com/user116435