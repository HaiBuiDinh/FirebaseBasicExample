package com.ohno.moneymanagerrefactor.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.FragmentHomeBinding
import com.ohno.moneymanagerrefactor.databinding.TestBinding
import com.ohno.moneymanagerrefactor.ui.activity.SignInActivity
import com.ohno.moneymanagerrefactor.util.PageUtils

class HomeFragment: AbsFragment() {

    private lateinit var binding: TestBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun layoutId(): Int = R.layout.test

    override fun init(rootView: View) {
        binding = TestBinding.bind(rootView)

//        firebaseAuth = FirebaseAuth.getInstance()
//
//        binding.btnLogOut.setOnClickListener {
//            firebaseAuth.signOut()
//            Toast.makeText(context, "Sign out!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(requireActivity(), SignInActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//        }
//
//        binding.btnGoToFirestore.setOnClickListener {
//            PageUtils.replaceFragment(requireActivity(), FirestoreTestFragment())
//        }
    }
}