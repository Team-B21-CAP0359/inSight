package com.bangkit.capstone.insightapp.viewmodel

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.model.ShopModel
import com.bangkit.capstone.insightapp.view.detail.ShopActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ShopListAdapter(private val ShopList: ArrayList<ShopModel>) :
    RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {

    private lateinit var mAuth: FirebaseAuth

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imagePath: ImageView = itemView.findViewById(R.id.avatar_barang)
        val namaBarang: TextView = itemView.findViewById(R.id.nama_barang)
        val deskripsiBarang: TextView = itemView.findViewById(R.id.genre_barang)
        val hargaBarang: TextView = itemView.findViewById(R.id.deskripsi_barang)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return ViewHolder(itemView)
    }


    override fun getItemCount(): Int = ShopList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = ShopList[position]
        val fileName = currentItem.uid
        val storageRefShirt =
            FirebaseStorage.getInstance().reference.child("images/$fileName-shop/barang")
        val localFile = File.createTempFile("tempImage", "jpg")
        storageRefShirt.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.imagePath.setImageBitmap(bitmap)
        }
        holder.namaBarang.text = currentItem.nama_barang
        holder.hargaBarang.text = currentItem.deskripsi_barang
        holder.deskripsiBarang.text = currentItem.harga_barang


        val shopData = ShopModel(
            currentItem.alamat_perusahaan,
            currentItem.deskripsi_barang,
            currentItem.email,
            currentItem.harga_barang,
            currentItem.nama_barang,
            currentItem.nama_perusahaan,
            currentItem.profile_photo,
            currentItem.search,
            currentItem.uid,
            currentItem.username,
            currentItem.wa_penjual
        )

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(holder.itemView.context, ShopActivity::class.java)
            moveDetail.putExtra(ShopActivity.EXTRA_DETAIL3, shopData)
            holder.itemView.context.startActivity(moveDetail)
        }
    }

}