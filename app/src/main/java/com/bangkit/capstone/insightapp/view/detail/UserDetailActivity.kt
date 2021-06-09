package com.bangkit.capstone.insightapp.view.detail

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

        supportActionBar?.title = "Detail User"

        _binding2 = binding.content

        setData()
        loadImage()

        bindingDetail.loadImage.visibility = View.VISIBLE

//        binding.btnPesan.setOnClickListener {
//            reportEmail()
//        }

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

    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<UserModel>(EXTRA_DETAIL) as UserModel
        bindingDetail.etNameUser.text = dataUser.username
        bindingDetail.etEmailUser.text = dataUser.email
        Glide.with(this)
            .load(dataUser.profile_photo).apply(RequestOptions())
            .into(binding.ivDetailImage)
        bindingDetail.styleCelana.text = dataUser.jenis_celana
        bindingDetail.styleSepatu.text = dataUser.jenis_sepatu
        bindingDetail.styleBaju.text = dataUser.jenis_shirt
        bindingDetail.namaCelana.text = dataUser.nama_celana
        bindingDetail.namaSepatu.text = dataUser.nama_sepatu
        bindingDetail.namaBaju.text = dataUser.nama_shirt
    }


    private fun loadImage() {
        val dataUser = intent.getParcelableExtra<UserModel>(EXTRA_DETAIL) as UserModel
        val storageRefShirt =
            FirebaseStorage.getInstance().reference.child("images/${dataUser.uid}-image/shirt")
        val storageRefPants =
            FirebaseStorage.getInstance().reference.child("images/${dataUser.uid}-image/pants")
        val storageRefShoe =
            FirebaseStorage.getInstance().reference.child("images/${dataUser.uid}-image/shoe")

        val localFile = File.createTempFile("tempImage", "jpg")
        val localFile2 = File.createTempFile("tempImage", "jpg")
        val localFile3 = File.createTempFile("tempImage", "jpg")
        storageRefShirt.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            bindingDetail.loadImage.visibility = View.GONE
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