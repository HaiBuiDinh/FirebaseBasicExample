package com.ohno.moneymanagerrefactor.data.firestore

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.ohno.moneymanagerrefactor.data.firestore.model.Course
import com.ohno.moneymanagerrefactor.ui.fragment.FirestoreTestFragment
import com.ohno.moneymanagerrefactor.util.CourseUtil
import javax.inject.Inject

class FirestoreDataSource @Inject constructor() {
    
    companion object {
        private const val TAG = "FirestoreDataSource"
    }

    fun getDataFromFirebase(dbRef: CollectionReference, callBack: (listData: List<Course>) -> Unit) {
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

    fun addRandomCourse(db: FirebaseFirestore, uId: String?) {
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

    fun observerFirebaseData(dbRef: CollectionReference, callBack: (listData: List<Course>) -> Unit) {
        dbRef.orderBy("duration", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (value != null) {
                Log.d(TAG, "data change!")
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

}