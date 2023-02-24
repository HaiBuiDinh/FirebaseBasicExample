package com.ohno.moneymanagerrefactor.ui.fragment

import android.util.Log
import android.view.View
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

class FirestoreTestFragment : AbsFragment() {

    private lateinit var binding: FragmentFirestoreBinding

    private lateinit var db: FirebaseFirestore

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var mAdapter: CourseAdapter

    private var uId: String? = null

    override fun layoutId(): Int = R.layout.fragment_firestore

    override fun init(rootView: View) {
        binding = FragmentFirestoreBinding.bind(rootView)

        firebaseAuth = Firebase.auth
        uId = firebaseAuth.currentUser?.uid
        db = Firebase.firestore

        mAdapter = CourseAdapter()
        binding.rcvCourse.adapter = mAdapter

        binding.btnGetRandomCourse.setOnClickListener {
            addRandomCourse()
        }

        val dbRef = db.collection("Courses").document(uId!!).collection("CourseUID")

        observerFirebaseData(dbRef) { newListCourse ->
            mAdapter.setListItem(newListCourse)
        }
    }

    private fun observerFirebaseData(dbRef: CollectionReference, callBack: (listData: List<Course>) -> Unit) {
        dbRef.orderBy("duration", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (value != null) {
                Log.d(FirestoreTestFragment.TAG, "data change!")
                val listDocument = value.documents
                val listCourse: ArrayList<Course> = arrayListOf()
                listDocument.forEach {
                    it?.let {
                        listCourse.add(it.toObject<Course>()!!)
                    }
                }
                callBack(listCourse)
            }
        }
    }

    private fun addRandomCourse() {
        val course = CourseUtil.getRandomCourse()
        Log.d(
            TAG,
            "Random course: name = ${course.name}, description = ${course.description}, duration = ${course.duration}"
        )

        db.collection("Courses").document(uId!!).collection("CourseUID")
            .add(course).addOnSuccessListener { task ->
                Log.d(TAG, "Add success with ID: ${task.id}")
            }
            .addOnFailureListener { task ->
                Log.d(TAG, "Add fail ${task.toString()}")
            }
    }

    private fun getDataFromFirebase(dbRef: CollectionReference, callBack: (listData: List<Course>) -> Unit) {
        dbRef.orderBy("duration", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { it ->
                if (!it.isEmpty) {
                    val listCourse: ArrayList<Course> = arrayListOf()
                    val listDocSnap = it.documents
                    listDocSnap.forEach { docSnap ->
                        Log.d(TAG, docSnap.data.toString())

                        docSnap?.let {
                            val tempCourse = docSnap.toObject<Course>()
                            if (tempCourse != null) listCourse.add(tempCourse)
                        }

                    }
                    callBack(listCourse)
                } else {
                    Log.d(TAG, "No data found in DB")
                }
            }
    }

    companion object {
        const val TAG = "FirestoreTestFragment"
    }

}