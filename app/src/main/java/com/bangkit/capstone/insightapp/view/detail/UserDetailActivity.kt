package com.bangkit.capstone.insightapp.view.detail

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.databinding.ActivityUserDetailBinding
import com.bangkit.capstone.insightapp.databinding.ContentUserListBinding
import com.bangkit.capstone.insightapp.model.UserModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class UserDetailActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!

    private var _binding2: ContentUserListBinding? = null
    private val bindingDetail get() = _binding2!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        _binding2 = binding.content

        setData()
        loadImage()

//        binding.btnPesan.setOnClickListener {
//            reportEmail()
//        }

    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<UserModel>(EXTRA_DETAIL) as UserModel
        bindingDetail.etNameUser.text = dataUser.username
        bindingDetail.etEmailUser.text = dataUser.email
        Glide.with(this)
            .load(dataUser.profile_photo).apply(RequestOptions())
            .into(binding.ivDetailImage)
    }


    private fun reportEmail() {
        val dataUser = intent.getParcelableExtra<UserModel>(EXTRA_DETAIL) as UserModel
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(dataUser.email.toString()))
        i.putExtra(Intent.EXTRA_SUBJECT, "Hay, Apa Kabar Pegiat Fashion")
        i.putExtra(Intent.EXTRA_TEXT, " ")
        try
        {
            startActivity(Intent.createChooser(i, "Send mail..."))
        }
        catch (ex:android.content.ActivityNotFoundException) {
            Toast.makeText(this, "Tidak ada aplikasi email terinstall", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImage() {
        val dataUser = intent.getParcelableExtra<UserModel>(EXTRA_DETAIL) as UserModel
        val storageRefShirt =
            FirebaseStorage.getInstance().reference.child("images/${dataUser.uid}-shirt")
        val storageRefPants =
            FirebaseStorage.getInstance().reference.child("images/${dataUser.uid}-pants")
        val storageRefShoe =
            FirebaseStorage.getInstance().reference.child("images/${dataUser.uid}-shoe")

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

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

}