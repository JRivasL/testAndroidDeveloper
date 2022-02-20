package com.rivas.testandroiddeveloper.ui.main.images.di

import com.rivas.testandroiddeveloper.ui.main.images.ImagesViewModel
import dagger.Module
import dagger.Provides

@Module
class ImagesModule {

    @Provides
    fun provideViewModel() = ImagesViewModel()
}