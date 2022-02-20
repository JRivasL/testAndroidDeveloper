package com.rivas.testandroiddeveloper.di


import com.rivas.testandroiddeveloper.ui.main.images.ImagesFragment
import com.rivas.testandroiddeveloper.ui.main.images.di.ImagesModule
import com.rivas.testandroiddeveloper.ui.main.map.di.MapModule
import com.rivas.testandroiddeveloper.ui.main.map.MapFragment
import com.rivas.testandroiddeveloper.ui.main.movies.MoviesFragment
import com.rivas.testandroiddeveloper.ui.main.movies.di.MoviesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [ImagesModule::class])
    abstract fun bindImages(): ImagesFragment

    @ContributesAndroidInjector(modules = [MoviesModule::class])
    abstract fun bindMovies(): MoviesFragment

    @ContributesAndroidInjector(modules = [MapModule::class])
    abstract fun bindMap(): MapFragment
}