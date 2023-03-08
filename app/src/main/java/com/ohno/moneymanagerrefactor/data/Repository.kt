package com.ohno.moneymanagerrefactor.data

import com.ohno.moneymanagerrefactor.data.firestore.FirestoreDataSource
import com.ohno.moneymanagerrefactor.data.firestore.RecordDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    firestoreDataSource: FirestoreDataSource,
    recordDataSource: RecordDataSource
){
    val mFireStoreData = firestoreDataSource
    val mRecordData = recordDataSource
}