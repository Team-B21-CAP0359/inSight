package com.bangkit.capstone.insightapp.view.activity.ui.shoe
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.model.UserModel
import com.google.firebase.database.DatabaseReference

class ShoeFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var dbRef : DatabaseReference
    private lateinit var userRecylerView : RecyclerView
    private lateinit var userArrayList: ArrayList<UserModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel =
            ViewModelProvider(this).get(UserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        userViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }


}