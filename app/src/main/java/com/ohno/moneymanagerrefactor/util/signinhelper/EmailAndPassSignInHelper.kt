package com.ohno.moneymanagerrefactor.util.signinhelper

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.ohno.moneymanagerrefactor.ui.fragment.HomeFragment
import com.ohno.moneymanagerrefactor.util.PageUtils

object EmailAndPassSignInHelper {
    private const val TAG = "EmailAndPassSignInHelper"

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        activity: FragmentActivity,
        firebaseAuth: FirebaseAuth
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // TO-DO check xem email đã đúng định dạng chưa
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    PageUtils.replaceFragment(activity, HomeFragment())
                    Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, it.exception.toString())
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, "The password is invalid or the user does not have a password.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(context, "Please fill all field!", Toast.LENGTH_SHORT).show()
        }
    }
}