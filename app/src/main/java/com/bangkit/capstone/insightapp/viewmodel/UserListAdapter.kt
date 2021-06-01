package com.bangkit.capstone.insightapp.viewmodel

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.model.UserModel
import com.bumptech.glide.Glide

class UserListAdapter(private val userList : ArrayList<UserModel>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName : TextView = itemView.findViewById(R.id.username)
        val userPhoto : ImageView = itemView.findViewById(R.id.avatar)
        val email : TextView = itemView.findViewById(R.id.email)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.userName.text = currentItem.username
        holder.email.text = currentItem.email
        val url = currentItem.profile_photo
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.userPhoto)

    }

    override fun getItemCount(): Int = userList.size


}