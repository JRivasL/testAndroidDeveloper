package com.rivas.testandroiddeveloper.di

import androidx.room.Room
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.repository.room.TestDataBase
import com.rivas.testandroiddeveloper.data.MovieDao
import com.rivas.testandroiddeveloper.repository.room.movie.MovieDataSource
import com.rivas.testandroiddeveloper.repository.room.movie.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    private val demoDatabase: TestDataBase =
        Room.databaseBuilder(AndroidApp.appContext, TestDataBase::class.java, "test-parrot.bd").build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): TestDataBase {
        return demoDatabase
    }

    @Singleton
    @Provides
    fun providesMovieDao(demoDatabase: TestDataBase): MovieDao {
        return demoDatabase.movieDao
    }

    @Singleton
    @Provides
    fun movieRepository(movieDao: MovieDao): MovieRepository {
        return MovieDataSource(movieDao)
    }

}