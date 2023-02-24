package com.ohno.moneymanagerrefactor.util.signinhelper

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.facebook.AccessToken
import com.facebook.appevents.codeless.internal.PathComponent
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.ohno.moneymanagerrefactor.ui.fragment.HomeFragment
import com.ohno.moneymanagerrefactor.ui.fragment.SignInFragment
import com.ohno.moneymanagerrefactor.util.PageUtils

object FacebookSignInHelper {

    private const val TAG = "FacebookSignInHelper"
    private fun handleFacebookAccessToken(
        token: AccessToken,
        activity: FragmentActivity,
        context: Context,
        firebaseAuth: FirebaseAuth
    ) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
//        showProgressBar()

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    PageUtils.replaceFragment(activity, HomeFragment())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

//                hideProgressBar()
            }
    }
}