package com.ohno.moneymanagerrefactor.di

import com.ohno.moneymanagerrefactor.data.firestore.FirestoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

//    @Singleton
//    @Provides
//    fun provideFireStoreData() = FirestoreDataSource()
}