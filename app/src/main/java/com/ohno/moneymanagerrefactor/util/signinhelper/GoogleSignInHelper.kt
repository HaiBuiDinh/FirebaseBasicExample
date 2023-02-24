package com.ohno.moneymanagerrefactor.util.signinhelper

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.ui.fragment.HomeFragment
import com.ohno.moneymanagerrefactor.util.PageUtils

object GoogleSignInHelper {

    private const val TAG = "GoogleSignInHelper"


    fun signInWithGoogle(
        context: Context,
        signInClient: SignInClient,
        signInLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        val signInRequest = GetSignInIntentRequest.builder()
            .setServerClientId(context.getString(R.string.your_web_client_id))
            .build()

        signInClient.getSignInIntent(signInRequest)
            .addOnSuccessListener { pendingIntent ->
                launchSignIn(pendingIntent, signInLauncher)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Google Sign-in failed", e)
            }
    }

    //Cái này dùng cho trường hợp chỉ có gg sign in và n hiện lên luôn
    fun oneTapSignIn(
        context: Context,
        signInClient: SignInClient,
        signInLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(context.getString(R.string.your_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()

        //Display the One Tap UI
        signInClient.beginSignIn(signInRequest).addOnSuccessListener { result ->
            launchSignIn(result.pendingIntent, signInLauncher)
        }.addOnFailureListener { e -> }
    }

    private fun launchSignIn(
        pendingIntent: PendingIntent,
        signInLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        try {
            val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
            signInLauncher.launch(intentSenderRequest)
        } catch (e: IntentSender.SendIntentException) {
            Log.e(TAG, "Couldn't start Sign In: ${e.localizedMessage}")
        }
    }

    fun handleSignInResult(
        activity: FragmentActivity,
        data: Intent?,
        signInClient: SignInClient,
        firebaseAuth: FirebaseAuth
    ) {
        // Result returned from launching the Sign In PendingIntent
        try {
            val credential = signInClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                Log.d(TAG, "firebaseAuthWithGoogle: ${credential.id}")
                firebaseAuthWithGoogle(activity, idToken, firebaseAuth)
            } else {
                // Shouldn't happen.
                Log.d(TAG, "No ID token!")
            }
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)
            Toast.makeText(activity, "Google sign in failed.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun firebaseAuthWithGoogle(
        activity: FragmentActivity,
        idToken: String,
        firebaseAuth: FirebaseAuth
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    PageUtils.replaceFragment(activity, HomeFragment())
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(activity, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}