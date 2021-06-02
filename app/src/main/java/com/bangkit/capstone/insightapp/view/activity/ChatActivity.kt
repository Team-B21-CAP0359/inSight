package com.bangkit.capstone.insightapp.view.activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityChatBinding
import com.bangkit.capstone.insightapp.model.UserModel
import com.bangkit.capstone.insightapp.viewmodel.UserListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detail_user, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchForUser(query.toLowerCase(Locale.ROOT))
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        return true
    }

    private fun searchForUser(str: String) {

        FirebaseAuth.getInstance().currentUser?.uid
        val queryUser = FirebaseDatabase.getInstance().reference
            .child("Users").orderByChild("search")
            .startAt(str)
            .endAt(str + "\uf8ff")

        queryUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UserModel::class.java)
                        userArrayList.clear()
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