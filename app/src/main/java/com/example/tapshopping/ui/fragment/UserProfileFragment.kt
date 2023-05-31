package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tapshopping.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val bottomNavigationView =
//            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
//        bottomNavigationView.visibility = View.GONE
    }

}