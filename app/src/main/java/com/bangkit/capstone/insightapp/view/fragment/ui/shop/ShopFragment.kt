package com.bangkit.capstone.insightapp.view.fragment.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.databinding.FragmentShopBinding
import com.bangkit.capstone.insightapp.databinding.FragmentUserBinding
import com.bangkit.capstone.insightapp.model.GoodModel
import com.bangkit.capstone.insightapp.model.ShopModel
import com.bangkit.capstone.insightapp.viewmodel.GoodListAdapter
import com.bangkit.capstone.insightapp.viewmodel.ShopListAdapter
import com.google.firebase.database.*


class ShopFragment : Fragment() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArrayList: ArrayList<ShopModel>

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRecyclerView = binding.rvShop
        userRecyclerView.layoutManager = LinearLayoutManager(view.context)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()

    }

    private fun getUserData() {

        binding.progressBarProfil.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("barangJual")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(ShopModel::class.java)
                        userArrayList.add(user!!)
                        binding.progressBarProfil.visibility = View.GONE
                    }
                    binding.progressBarProfil.visibility = View.GONE
                    userRecyclerView.adapter = ShopListAdapter(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                binding.progressBarProfil.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}