package com.ohno.moneymanagerrefactor.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.ActivitySignInBinding
import com.ohno.moneymanagerrefactor.ui.fragment.SignInFragment
import com.ohno.moneymanagerrefactor.ui.fragment.SignUpFragment
import com.ohno.moneymanagerrefactor.util.PageUtils
import com.ohno.moneymanagerrefactor.util.PageUtils.replaceFragment

class SignInActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.hide()

        PageUtils.replaceSignFragment(this, SignInFragment())
    }


}