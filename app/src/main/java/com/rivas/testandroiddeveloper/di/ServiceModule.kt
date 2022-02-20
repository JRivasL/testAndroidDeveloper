package com.rivas.testandroiddeveloper.di

import com.rivas.testandroiddeveloper.services.ServiceSaveLocation
import com.rivas.testandroiddeveloper.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun bindService(): ServiceSaveLocation
}