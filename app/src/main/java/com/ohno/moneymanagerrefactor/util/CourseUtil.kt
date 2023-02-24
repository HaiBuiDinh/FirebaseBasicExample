package com.ohno.moneymanagerrefactor.util

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.ohno.moneymanagerrefactor.data.firestore.model.Course
import com.ohno.moneymanagerrefactor.ui.fragment.FirestoreTestFragment
import java.util.Random

object CourseUtil {

    private val COURSE_NAME_LIST = arrayOf("Java", "Python", "Kotlin", "Dart", "C++", "C#")
    private val COURSE_DESCRIPTION_LIST = arrayOf(
        "Java course",
        "Python course",
        "Kotlin course",
        "Dart course",
        "C++ course",
        "C# course"
    )

    /*
    * Create a random Course POJO
     */
    fun getRandomCourse() : Course {
        val course = Course()
        val random = Random()

        course.name = getRandomName(random)
        course.duration = kotlin.random.Random.nextLong(1000L, 10000L)
        course.description = getDescription(random)

        return course
    }

    private fun getRandomName(random: Random): String {
        return getRandomString(COURSE_NAME_LIST, random)
    }

    private fun getDescription(random: Random): String {
        return getRandomString(COURSE_DESCRIPTION_LIST, random)
    }

    private fun getRandomString(array: Array<String>, random: Random) : String {
        val ind = random.nextInt(array.size)
        return array[ind]
    }

    fun testOnChange(dbRef: CollectionReference, callBack: (listData: List<Course>) -> Unit) {
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

}