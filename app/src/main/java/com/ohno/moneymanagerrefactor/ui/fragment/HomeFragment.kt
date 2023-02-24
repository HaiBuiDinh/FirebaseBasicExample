package com.ohno.moneymanagerrefactor.ui.fragment

import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.FragmentHomeBinding
import com.ohno.moneymanagerrefactor.util.PageUtils

class HomeFragment: AbsFragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun layoutId(): Int = R.layout.fragment_home

    override fun init(rootView: View) {
        binding = FragmentHomeBinding.bind(rootView)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogOut.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(context, "Sign out!", Toast.LENGTH_SHORT).show()
            PageUtils.replaceFragment(requireActivity(), SignInFragment())
        }

        binding.btnGoToFirestore.setOnClickListener {
            PageUtils.replaceFragment(requireActivity(), FirestoreTestFragment())
        }
    }
}