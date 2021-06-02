package com.bangkit.capstone.insightapp.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.databinding.ActivityUserDetailBinding
import com.bangkit.capstone.insightapp.model.UserModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserDetailActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setData()

        binding.btnPesan.setOnClickListener {
            reportEmail()
        }

    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<UserModel>(EXTRA_DETAIL) as UserModel
        binding.etNameUser.text = dataUser.username
        binding.etEmailUser.text = dataUser.email
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

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

}