package com.ohno.moneymanagerrefactor.ui.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.databinding.DialogSetNameBinding

class EditNameDialog: AbsDialog(), TextWatcher {

    private lateinit var mBinding: DialogSetNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_set_name, container, false)
        mBinding = DialogSetNameBinding.bind(view)

        mBinding.btnSetName.setOnClickListener {
            mCallback.onSetName(this, mBinding.edtUserName.text.toString())
        }

        mBinding.edtUserName.addTextChangedListener(this)

        setOkButtonState(false)
        return view
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        setOkButtonState(s!!.isNotEmpty())
    }

    override fun afterTextChanged(s: Editable?) {

    }

    private fun setOkButtonState(enable: Boolean) {
        mBinding.btnSetName.isEnabled = enable
        mBinding.btnSetName.alpha = if (enable) 1.0f else 0.4f
        mBinding.btnSetName.isFocusable = enable
    }
}