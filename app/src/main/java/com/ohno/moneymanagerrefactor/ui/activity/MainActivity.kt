package com.ohno.moneymanagerrefactor.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.ActivityMainBinding
import com.ohno.moneymanagerrefactor.ui.fragment.HomeFragment
import com.ohno.moneymanagerrefactor.ui.fragment.SignInFragment
import com.ohno.moneymanagerrefactor.util.PageUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            reload()
        } else {
            PageUtils.replaceFragment(this, SignInFragment())
        }
    }

    private fun reload() {
        firebaseAuth.currentUser!!.reload().addOnCompleteListener {
            if (it.isSuccessful) {
                PageUtils.replaceFragment(this, HomeFragment())
            } else {
                Toast.makeText(this, "Reload failed, please sign in again!", Toast.LENGTH_SHORT).show()
                PageUtils.replaceFragment(this, SignInFragment())
            }
        }
    }
}