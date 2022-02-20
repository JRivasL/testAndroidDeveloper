package com.rivas.testandroiddeveloper.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivas.testandroiddeveloper.data.Movie
import com.rivas.testandroiddeveloper.data.MovieDao

@Database(entities = [Movie::class], version = TestDataBase.VERSION, exportSchema = false)
abstract class TestDataBase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        const val VERSION = 1
    }
}