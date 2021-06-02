package com.bangkit.capstone.insightapp.viewmodel

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.model.GoodModel
import com.bangkit.capstone.insightapp.model.UserModel
import com.bangkit.capstone.insightapp.view.detail.GoodDetailActivity
import com.bangkit.capstone.insightapp.view.detail.UserDetailActivity
import com.bumptech.glide.Glide

class GoodListAdapter(private val goodList : ArrayList<GoodModel>) : RecyclerView.Adapter<GoodListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePath : ImageView = itemView.findViewById(R.id.avatar_barang)
        val shirtName : TextView = itemView.findViewById(R.id.nama_barang)
        val shirt : TextView = itemView.findViewById(R.id.genre_barang)
        val shirtDescription : TextView = itemView.findViewById(R.id.deskripsi_barang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return GoodListAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = goodList[position]

        holder.shirtName.text = currentItem.shirtName
        holder.shirt.text = currentItem.shirt
        holder.shirtDescription.text = currentItem.shirtDescription
        val url = currentItem.imagePath
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.imagePath)

        val goodData = GoodModel(
            currentItem.imagePath,
            currentItem.shirt,
            currentItem.shirtDescription,
            currentItem.shirtName,
        )

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(holder.itemView.context, GoodDetailActivity::class.java)
            moveDetail.putExtra(GoodDetailActivity.EXTRA_DETAIL2, goodData)
            holder.itemView.context.startActivity(moveDetail)
        }

    }

    override fun getItemCount(): Int = goodList.size
}