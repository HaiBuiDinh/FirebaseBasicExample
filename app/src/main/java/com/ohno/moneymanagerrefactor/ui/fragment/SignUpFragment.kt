package com.ohno.moneymanagerrefactor.ui.fragment

import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.FragmentSignUpBinding
import com.ohno.moneymanagerrefactor.util.PageUtils

class SignUpFragment: AbsFragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun layoutId(): Int = R.layout.fragment_sign_up

    override fun init(rootView: View) {
        binding = FragmentSignUpBinding.bind(rootView)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvGoToSignIn.setOnClickListener {
            PageUtils.replaceSignFragment(requireActivity(), SignInFragment())
        }
        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            PageUtils.replaceSignFragment(requireActivity(), SignInFragment())
                        } else {
                            Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Password and confirm password are not matching!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please fill all field!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}