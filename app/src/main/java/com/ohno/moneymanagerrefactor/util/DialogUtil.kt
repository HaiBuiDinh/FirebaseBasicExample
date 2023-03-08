package com.ohno.moneymanagerrefactor.util

import androidx.fragment.app.FragmentManager
import com.ohno.moneymanagerrefactor.ui.dialog.EditNameDialog
import com.ohno.moneymanagerrefactor.ui.dialog.UserInteractionDialog

object DialogUtil {
    private const val TAG = "DialogUtil"

    fun showEditUserNameDialog(
        supportFragmentManager: FragmentManager,
        allowCancellable: Boolean,
        callback:(name: String) -> Unit
    ) {
        EditNameDialog().apply {
            setDialogInfos(supportFragmentManager)
            setCanCancel(allowCancellable)
        }.showDialog(object : UserInteractionDialog.Callback {
            override fun onSetName(dialog: UserInteractionDialog?, input: String) {
                dialog?.let {
                    if (input.isNotEmpty()){
                        callback(input)
                    }
                }
                dialog?.dismissDialog()
            }

            override fun onCancel(dialog: UserInteractionDialog?) {
                //Do nothing
            }
        })
    }
}