package com.ohno.moneymanagerrefactor.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ohno.moneymanagerrefactor.R

abstract class AbsDialog: BottomSheetDialogFragment(), UserInteractionDialog {

    open lateinit var mCallback: UserInteractionDialog.Callback
    open var mTag = this.javaClass.simpleName
    open lateinit var mFragmentManager: FragmentManager
    open var mAllowCancellable: Boolean = true

    fun setDialogInfos(fm: FragmentManager) {
        mFragmentManager = fm
    }
    open fun setCanCancel(allow: Boolean) {
        mAllowCancellable = allow
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        isCancelable = mAllowCancellable
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }

        return dialog
    }

    override fun showDialog(callback: UserInteractionDialog.Callback) {
        mCallback = callback
        mFragmentManager.let {
            show(mFragmentManager, mTag)
        }
    }

    override fun dismissDialog() {
        if (isAdded) dismiss()
    }

    override fun activity(): FragmentActivity {
        return requireActivity()
    }
}