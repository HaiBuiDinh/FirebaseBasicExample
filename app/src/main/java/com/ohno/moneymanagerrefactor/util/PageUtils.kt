package com.ohno.moneymanagerrefactor.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ohno.moneymanagerrefactor.R

object PageUtils {

    fun replaceFragment(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, fragment)
            .commit()
    }
}