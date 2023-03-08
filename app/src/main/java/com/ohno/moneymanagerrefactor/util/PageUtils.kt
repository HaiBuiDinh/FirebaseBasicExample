package com.ohno.moneymanagerrefactor.util

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ohno.moneymanagerrefactor.R
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object PageUtils {

    fun goToActivity(activity: FragmentActivity, cls: Class<*>) {
        val intent = Intent(activity, cls)
        activity.startActivity(intent)
        activity.finish()
    }

    fun replaceFragment(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, fragment)
            .commit()
    }

    fun replaceSignFragment(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.sign_in_frame, fragment)
            .commit()
    }


}