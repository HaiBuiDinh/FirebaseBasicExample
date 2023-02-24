package com.ohno.moneymanagerrefactor.ui.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AbsAdapter<T, V : AbsViewHolder<T>> : RecyclerView.Adapter<V>(){

    private var mListItem: List<T> = ArrayList()
    protected var mOnItemClick: OnItemClick<T>? = null

    open fun setListItem(list: List<T>) {
        mListItem = list
        notifyDataSetChanged()
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.bindView(mListItem.get(position), position)
    }

    override fun getItemCount(): Int {
        return mListItem.size
    }

    fun setOnItemClick(onItemClick: OnItemClick<T>) {
        mOnItemClick = onItemClick
        Log.d("AbsAdapter", "set on click: $mOnItemClick")
    }

    interface OnItemClick<T> {
        fun onClick(position: Int, item: T)
    }

}