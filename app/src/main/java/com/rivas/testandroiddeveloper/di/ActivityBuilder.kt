package com.rivas.testandroiddeveloper.di

import com.rivas.testandroiddeveloper.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMain(): MainActivity
}