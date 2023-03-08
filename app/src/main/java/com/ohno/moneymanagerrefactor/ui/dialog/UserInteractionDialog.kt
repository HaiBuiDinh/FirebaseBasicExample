package com.ohno.moneymanagerrefactor.ui.dialog

import androidx.fragment.app.FragmentActivity

interface UserInteractionDialog {

    fun showDialog(callback: Callback)
    fun dismissDialog()
    fun activity(): FragmentActivity

    interface Callback: java.io.Serializable {
        fun onOk(dialog: UserInteractionDialog?) {
            dialog?.dismissDialog()
        }

        fun onCancel(dialog: UserInteractionDialog?) {
            dialog?.dismissDialog()
        }

        fun onSetName(dialog: UserInteractionDialog?, input: String) {
            dialog?.dismissDialog()
        }
    }
}