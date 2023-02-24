package com.ohno.moneymanagerrefactor.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbsViewHolder<T>(val mView: View) : RecyclerView.ViewHolder(mView) {
    abstract fun bindView(info : T, position: Int)
}