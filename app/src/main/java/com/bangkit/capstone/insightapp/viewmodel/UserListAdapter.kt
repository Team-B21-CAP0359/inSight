package com.bangkit.capstone.insightapp.viewmodel

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.model.UserModel
import com.bangkit.capstone.insightapp.view.detail.UserDetailActivity
import com.bumptech.glide.Glide

class UserListAdapter(private val userList: ArrayList<UserModel>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.username)
        val userPhoto: ImageView = itemView.findViewById(R.id.avatar)
        val email: TextView = itemView.findViewById(R.id.email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.userName.text = currentItem.username
        holder.email.text = currentItem.email
        val url = currentItem.profile_photo
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.userPhoto)

        val dataUser = UserModel(
            currentItem.bio,
            currentItem.email,
            currentItem.jenis_celana,
            currentItem.jenis_sepatu,
            currentItem.jenis_shirt,
            currentItem.nama_celana,
            currentItem.nama_sepatu,
            currentItem.nama_shirt,
            currentItem.profile_photo,
            currentItem.search,
            currentItem.status,
            currentItem.uid,
            currentItem.username,

            )

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(holder.itemView.context, UserDetailActivity::class.java)
            moveDetail.putExtra(UserDetailActivity.EXTRA_DETAIL, dataUser)
            holder.itemView.context.startActivity(moveDetail)
        }

    }

    override fun getItemCount(): Int = userList.size


}