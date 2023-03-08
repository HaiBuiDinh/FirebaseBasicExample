package com.ohno.moneymanagerrefactor.ui.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohno.moneymanagerrefactor.R
import com.ohno.moneymanagerrefactor.data.firestore.model.Course
import com.ohno.moneymanagerrefactor.databinding.FragmentFirestoreBinding
import com.ohno.moneymanagerrefactor.ui.adapter.CourseAdapter
import com.ohno.moneymanagerrefactor.util.CourseUtil
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirestoreTestFragment : AbsFragment() {

    private lateinit var binding: FragmentFirestoreBinding

    private lateinit var db: FirebaseFirestore

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var mAdapter: CourseAdapter

    private var uId: String? = null

    private val mFireStoreViewModel: FireStoreViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_firestore

    override fun init(rootView: View) {
        binding = FragmentFirestoreBinding.bind(rootView)

        firebaseAuth = Firebase.auth
        uId = firebaseAuth.currentUser?.uid
        db = Firebase.firestore

        mAdapter = CourseAdapter()
        binding.rcvCourse.adapter = mAdapter

        binding.btnGetRandomCourse.setOnClickListener {
            mFireStoreViewModel.addRandomCourse(db, uId)
        }

        val dbRef = db.collection("Courses").document(uId!!).collection("CourseUID")

        mFireStoreViewModel.observerDataFromFirebase(dbRef) { newListCourse ->
            lifecycleScope.launch {
                mAdapter.setListItem(newListCourse)
            }
        }
    }

    companion object {
        const val TAG = "FirestoreTestFragment"
    }

}