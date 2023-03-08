package com.ohno.moneymanagerrefactor.data.firestore

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.ohno.moneymanagerrefactor.data.firestore.model.Record
import javax.inject.Inject
import com.google.firebase.firestore.ktx.toObject

class RecordDataSource @Inject constructor(

) {

    companion object {
        private const val TAG = "RecordDataSource"
    }

    fun addRecordToDb(dbRef: CollectionReference, record: Record) {
        dbRef.add(record).addOnSuccessListener { task ->
            Log.d(TAG, "Add success with ID: ${task.id}")
        }.addOnFailureListener { e ->
            Log.d(TAG, "Add fail ${e.toString()}")
        }
    }

    fun getAllRecordsFromDb(
        dbRef: CollectionReference,
        callback: (recordList: List<Record>) -> Unit
    ) {
        dbRef.orderBy(Record.TIME, Query.Direction.DESCENDING).get()
            .addOnSuccessListener { snapShot ->
                val listRecord: ArrayList<Record> = arrayListOf()
                val listDocSnap = snapShot.documents
                listDocSnap.forEach { item ->
                    Log.d(TAG, item.data.toString())
                    item?.let {
                        it.toObject<Record>()?.let { tempRecord ->
                            listRecord.add(tempRecord)
                        }
                    }
                }
                callback(listRecord)
            }.addOnFailureListener { e ->
        }
    }
}