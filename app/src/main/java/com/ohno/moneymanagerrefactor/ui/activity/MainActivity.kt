package com.ohno.moneymanagerrefactor.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.ActivityMainBinding
import com.ohno.moneymanagerrefactor.ui.fragment.AbsFragment
import com.ohno.moneymanagerrefactor.ui.fragment.HomeFragment
import com.ohno.moneymanagerrefactor.ui.fragment.SignInFragment
import com.ohno.moneymanagerrefactor.util.DialogUtil
import com.ohno.moneymanagerrefactor.util.PageUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    val mListBottomNavigation by lazy {
        val A = 3
        val B=4
        HashMap<Int, BottomNavigationInfo>().apply {
            put(R.id.home, BottomNavigationInfo(HomeFragment(), firebaseAuth.currentUser?.displayName, true, true))
            put(R.id.history, BottomNavigationInfo(HomeFragment(), "History ${A+B}", true, false))
            put(R.id.graph, BottomNavigationInfo(HomeFragment(), "Graph", true, false))
            put(R.id.person, BottomNavigationInfo(HomeFragment(), "Personal", false, false))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        checkUserDisplayName()

        if (firebaseAuth.currentUser != null) reload()
        else {
            PageUtils.goToActivity(this, SignInActivity::class.java)
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            loadFragment(mListBottomNavigation.get(it.itemId))
            true
        }
    }

    private fun reload() {
        firebaseAuth.currentUser!!.reload().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, firebaseAuth.currentUser!!.displayName.toString())
                loadFragment(mListBottomNavigation.get(R.id.home))
            } else {
                Toast.makeText(this, "Reload failed, please sign in again!", Toast.LENGTH_SHORT).show()
                PageUtils.goToActivity(this, SignInActivity::class.java)
            }
        }
    }

    private fun loadFragment(info: BottomNavigationInfo?) {
        info?.let {
            if (it.isHome) {
                binding.toolbarMainTitle.text = "Welcome Back, ${info.mTitle}"
            } else {
                binding.toolbarMainTitle.text = info.mTitle
            }
            binding.toolbarNotiBtn.visibility = if (info.isShowNotiBtn) View.VISIBLE else View.GONE
            info.mFragment.let {
                PageUtils.replaceFragment(this, info.mFragment)
            }
        }
    }

    private fun checkUserDisplayName() {
        firebaseAuth.currentUser?.let { curUser ->
        if (curUser.displayName.isNullOrEmpty()) {
                DialogUtil.showEditUserNameDialog(supportFragmentManager, false) { newName ->
                    Log.d(TAG, "User set his/her name is: $newName")
                    binding.toolbarMainTitle.text = "Welcome Back, $newName"
                    val profileUpdate = userProfileChangeRequest {
                        displayName = newName
                    }

                    curUser.updateProfile(profileUpdate).addOnCompleteListener {
                        val temp = mListBottomNavigation.get(R.id.home)
                        temp?.apply {
                            mTitle = newName
                        }
                    }
                }
            }
        }
    }

    inner class BottomNavigationInfo(
        var mFragment: AbsFragment,
        var mTitle: String?,
        var isShowNotiBtn: Boolean,
        var isHome: Boolean
    )

    companion object {
        const val TAG = "MainActivity"
    }
}