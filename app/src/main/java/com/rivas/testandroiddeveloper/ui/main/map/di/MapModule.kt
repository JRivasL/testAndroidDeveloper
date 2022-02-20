package com.rivas.testandroiddeveloper.ui.main.map.di

import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.testandroiddeveloper.ui.main.map.MapViewModel
import dagger.Module
import dagger.Provides

@Module
class MapModule {
    @Provides
    fun provideViewModel(firestore: FirebaseFirestore) =
        MapViewModel(firestore)
}