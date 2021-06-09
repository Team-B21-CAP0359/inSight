package com.bangkit.capstone.insightapp.view.detail

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.databinding.ActivityShopBinding
import com.bangkit.capstone.insightapp.databinding.ContentShopDetailBinding
import com.bangkit.capstone.insightapp.model.ShopModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class ShopActivity : AppCompatActivity() {

    private var _binding: ActivityShopBinding? = null
    private val binding get() = _binding!!

    private var _binding2: ContentShopDetailBinding? = null
    private val bindingDetail get() = _binding2!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _binding2 = binding.detailShop

        supportActionBar?.hide()

        setData()
        loadImage()
        mAuth = FirebaseAuth.getInstance()

        bindingDetail.buyBtn.setOnClickListener {
            onClickWhatsApp()
        }

    }

    private fun loadImage() {
        val dataUser = intent.getParcelableExtra<ShopModel>(EXTRA_DETAIL3) as ShopModel
        val fileName = dataUser.uid
        val storageRefShirt =
            FirebaseStorage.getInstance().reference.child("images/$fileName-shop/barang")
        val localFile = File.createTempFile("tempImage", "jpg")
        storageRefShirt.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.imageShop.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to receive image", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<ShopModel>(EXTRA_DETAIL3) as ShopModel
        bindingDetail.goodName.text = dataUser.nama_barang
        bindingDetail.namaPerusahaan.text = dataUser.nama_perusahaan
        bindingDetail.hargaBarang.text = dataUser.harga_barang
        bindingDetail.deskripsiShop.text = dataUser.deskripsi_barang
    }

    fun onClickWhatsApp() {
        val dataUser = intent.getParcelableExtra<ShopModel>(EXTRA_DETAIL3) as ShopModel
        val phoneNumberWithCountryCode = dataUser.wa_penjual
        val message = "Hallo apakah barang ${dataUser.nama_barang} masih tersedia ?"

        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    String.format(
                        "https://api.whatsapp.com/send?phone=%s&text=%s",
                        phoneNumberWithCountryCode,
                        message
                    )
                )
            )
        )
    }

    companion object {
        const val EXTRA_DETAIL3 = "extra_detail"
    }


}