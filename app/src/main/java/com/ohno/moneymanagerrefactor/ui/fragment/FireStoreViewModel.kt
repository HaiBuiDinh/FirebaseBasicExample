package com.ohno.moneymanagerrefactor.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ohno.moneymanagerrefactor.data.Repository
import com.ohno.moneymanagerrefactor.data.firestore.FirestoreDataSource
import com.ohno.moneymanagerrefactor.data.firestore.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FireStoreViewModel @Inject constructor(
    private val mRepository: Repository
) : ViewModel() {

    fun addRandomCourse(db: FirebaseFirestore, uId: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.mFireStoreData.addRandomCourse(db, uId)
        }

    fun observerDataFromFirebase(dbRef: CollectionReference, callBack: (listData: List<Course>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.mFireStoreData.observerFirebaseData(dbRef, callBack)
        }
    }
}