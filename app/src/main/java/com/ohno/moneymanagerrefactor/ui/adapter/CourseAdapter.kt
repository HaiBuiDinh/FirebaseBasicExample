package com.ohno.moneymanagerrefactor.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.data.firestore.model.Course

class CourseAdapter: AbsAdapter<Course, CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(val itemView: View): AbsViewHolder<Course>(itemView) {

        private val mName: TextView = itemView.findViewById(R.id.name)
        private val mDescription: TextView = itemView.findViewById(R.id.description)
        private val mDuration: TextView = itemView.findViewById(R.id.duration)
        override fun bindView(info: Course, position: Int) {
            mName.text = info.name
            mDescription.text = info.description
            mDuration.text = "${info.duration} s"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false))
    }

    companion object {
        private const val TAG = "CourseAdapter"
    }

}