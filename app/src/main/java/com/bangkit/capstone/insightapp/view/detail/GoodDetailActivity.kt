package com.bangkit.capstone.insightapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.databinding.ActivityGoodDetailBinding
import com.bangkit.capstone.insightapp.databinding.ContentGoodDetailBinding
import com.bangkit.capstone.insightapp.model.GoodModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GoodDetailActivity : AppCompatActivity() {

    private var _binding: ActivityGoodDetailBinding? = null
    private val binding get() = _binding!!

    private var _binding2: ContentGoodDetailBinding? = null
    private val bindingDetail get() = _binding2!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityGoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _binding2 = binding.detailGood

        setData()

        supportActionBar?.hide()

    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<GoodModel>(EXTRA_DETAIL2) as GoodModel
        Glide.with(this)
            .load(dataUser.imagePath).apply(RequestOptions())
            .into(binding.imagePosterDetail)
        bindingDetail.goodName.text = dataUser.shirtName
        bindingDetail.deskripsiBarang.text = dataUser.shirtDescription
        bindingDetail.goodGender.text = dataUser.shirt
    }

    companion object {
        const val EXTRA_DETAIL2 = "extra_detail"
    }

}