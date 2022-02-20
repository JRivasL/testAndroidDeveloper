package com.rivas.testandroiddeveloper.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rivas.testandroiddeveloper.data.Movie


@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie WHERE page=:page")
    fun findByPage(page: String): LiveData<List<Movie>>

    @Query("SELECT * FROM Movie WHERE page=:page")
    fun findByPageLocal(page: String): List<Movie>

    @Query("SELECT * FROM Movie WHERE uid=:id")
    fun findById(id: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Movie?): Long

    @Update
    fun update(product: Movie)

    @Delete
    fun delete(product: Movie?): Int
}