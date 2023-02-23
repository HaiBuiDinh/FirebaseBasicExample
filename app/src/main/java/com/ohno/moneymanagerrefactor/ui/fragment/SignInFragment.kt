package com.ohno.moneymanagerrefactor.ui.fragment

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.FragmentSignInBinding
import com.ohno.moneymanagerrefactor.util.PageUtils
import kotlin.math.sign

class SignInFragment: AbsFragment() {
    private lateinit var binding: FragmentSignInBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var signInClient: SignInClient

    private lateinit var callbackManager: CallbackManager

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        handleSignInResult(result.data)
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
            signInWithEmailAndPassword()
        }

        binding.btnLoginGoogle.setOnClickListener {
            signInClient = Identity.getSignInClient(requireContext())
            signInWithGoogle()
        }

//        binding.btnLoginFb2.setPermissions("email", "public_profile")
//        binding.btnLoginFb2.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            override fun onCancel() {
//                Log.d(TAG, "facebook:onCancel")
//            }
//
//            override fun onError(error: FacebookException) {
//                Log.d(TAG, "facebook:onError")
//            }
//
//            override fun onSuccess(result: LoginResult) {
//                handleFacebookAccessToken(result.accessToken)
//            }
//
//        })
    }

    private fun signInWithEmailAndPassword() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    PageUtils.replaceFragment(requireActivity(), HomeFragment())
                } else {
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Please fill all field!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogle() {
        val signInRequest = GetSignInIntentRequest.builder()
            .setServerClientId(getString(R.string.your_web_client_id))
            .build()

        signInClient.getSignInIntent(signInRequest)
            .addOnSuccessListener { pendingIntent ->
                launchSignIn(pendingIntent)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Google Sign-in failed", e)
            }
    }

    //Cái này dùng cho trường hợp chỉ có gg sign in và n hiện lên luôn
    private fun oneTapSignIn() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.your_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()

        //Display the One Tap UI
        signInClient.beginSignIn(signInRequest).addOnSuccessListener { result ->
            launchSignIn(result.pendingIntent)
        }.addOnFailureListener { e ->  }
    }

    private fun launchSignIn(pendingIntent: PendingIntent) {
        try {
            val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
            signInLauncher.launch(intentSenderRequest)
        } catch (e: IntentSender.SendIntentException) {
            Log.e(TAG, "Couldn't start Sign In: ${e.localizedMessage}")
        }
    }

    private fun handleSignInResult(data: Intent?) {
        // Result returned from launching the Sign In PendingIntent
        try {
            val credential = signInClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                Log.d(TAG, "firebaseAuthWithGoogle: ${credential.id}")
                firebaseAuthWithGoogle(idToken)
            } else {
                // Shouldn't happen.
                Log.d(TAG, "No ID token!")
            }
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)
            Toast.makeText(requireActivity(), "Google sign in failed.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    PageUtils.replaceFragment(requireActivity(), HomeFragment())
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(requireActivity(), "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
//        showProgressBar()

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    PageUtils.replaceFragment(requireActivity(), HomeFragment())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

//                hideProgressBar()
            }
    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}