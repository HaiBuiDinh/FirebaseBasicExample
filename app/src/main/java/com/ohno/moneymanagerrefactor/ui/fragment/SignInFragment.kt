package com.ohno.moneymanagerrefactor.ui.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.FragmentSignInBinding
import com.ohno.moneymanagerrefactor.util.signinhelper.GoogleSignInHelper.handleSignInResult
import com.ohno.moneymanagerrefactor.util.signinhelper.GoogleSignInHelper.signInWithGoogle
import com.ohno.moneymanagerrefactor.util.PageUtils
import com.ohno.moneymanagerrefactor.util.signinhelper.EmailAndPassSignInHelper.signInWithEmailAndPassword

class SignInFragment: AbsFragment() {
    private lateinit var binding: FragmentSignInBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var signInClient: SignInClient

    private lateinit var callbackManager: CallbackManager

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        handleSignInResult(requireActivity(), result.data, signInClient, firebaseAuth)
    }


    override fun layoutId() = R.layout.fragment_sign_in

    override fun init(view: View) {
        binding = FragmentSignInBinding.bind(view)

        firebaseAuth = Firebase.auth

        callbackManager = CallbackManager.Factory.create()

        binding.tvGoToSignUp.setOnClickListener {
            PageUtils.replaceFragment(requireActivity(), SignUpFragment())
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            signInWithEmailAndPassword(email, password, requireContext(), requireActivity(), firebaseAuth)
        }

        binding.btnLoginGoogle.setOnClickListener {
            signInClient = Identity.getSignInClient(requireContext())
            signInWithGoogle(requireContext(), signInClient, signInLauncher)
        }

    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}