package com.rivas.testandroiddeveloper.ui.main.movies.di

import com.rivas.testandroiddeveloper.repository.Repository
import com.rivas.testandroiddeveloper.repository.room.TestDataBase
import com.rivas.testandroiddeveloper.ui.main.movies.MoviesViewModel
import com.rivas.testandroiddeveloper.repository.room.movie.MovieRepository
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {
    @Provides
    fun provideViewModel(repository: Repository, movieRepository: MovieRepository, dataBase: TestDataBase) =
        MoviesViewModel(repository, movieRepository, dataBase)
}