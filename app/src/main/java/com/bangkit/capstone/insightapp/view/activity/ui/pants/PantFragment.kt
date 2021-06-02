package com.bangkit.capstone.insightapp.view.activity.ui.pants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.FragmentDashboardBinding
import com.bangkit.capstone.insightapp.databinding.FragmentUserBinding
import com.bangkit.capstone.insightapp.model.GoodModel
import com.bangkit.capstone.insightapp.viewmodel.GoodListAdapter
import com.google.firebase.database.*

class PantFragment : Fragment() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArrayList: ArrayList<GoodModel>

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRecyclerView = binding.rvPants
        userRecyclerView.layoutManager = LinearLayoutManager(view.context)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()
    }

    private fun getUserData() {
        binding.progressBarProfil.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("pants")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(GoodModel::class.java)
                        userArrayList.add(user!!)
                        binding.progressBarProfil.visibility = View.GONE
                    }
                    binding.progressBarProfil.visibility = View.GONE
                    userRecyclerView.adapter = GoodListAdapter(userArrayList)
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