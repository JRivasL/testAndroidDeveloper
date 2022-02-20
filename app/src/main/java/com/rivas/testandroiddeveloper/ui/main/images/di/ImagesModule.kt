package com.rivas.testandroiddeveloper.ui.main.images.di

import com.google.firebase.storage.FirebaseStorage
import com.rivas.testandroiddeveloper.ui.main.images.ImagesViewModel
import dagger.Module
import dagger.Provides

@Module
class ImagesModule {

    @Provides
    fun provideViewModel(firebaseStorage: FirebaseStorage) = ImagesViewModel(firebaseStorage)
}