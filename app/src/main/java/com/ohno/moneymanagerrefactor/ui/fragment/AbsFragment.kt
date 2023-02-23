package com.ohno.moneymanagerrefactor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class AbsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(layoutId(), container, false)
        init(view)
        return view
    }

    abstract fun layoutId(): Int

    abstract fun init(rootView: View)

}