package com.bangkit.capstone.insightapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatViewInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityChatBinding
import com.bangkit.capstone.insightapp.databinding.ActivityLoginBinding
import com.bangkit.capstone.insightapp.model.UserModel
import com.bangkit.capstone.insightapp.viewmodel.UserListAdapter
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArrayList: ArrayList<UserModel>

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerView = binding.rvUser
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()

    }

    private fun getUserData() {
        binding.progressBarProfil.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UserModel::class.java)
                        userArrayList.add(user!!)
                        binding.progressBarProfil.visibility = View.GONE
                    }
                    binding.progressBarProfil.visibility = View.GONE
                    userRecyclerView.adapter = UserListAdapter(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                binding.progressBarProfil.visibility = View.GONE
            }
        })
    }
}