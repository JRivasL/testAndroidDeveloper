package com.rivas.testandroiddeveloper.repository.room.movie

import androidx.lifecycle.LiveData
import com.rivas.testandroiddeveloper.data.Movie

interface MovieRepository {
    fun findByPage(page:String): LiveData<List<Movie>>
    fun insert(product: Movie?): Long
    fun delete(product: Movie?): Int
    fun update(product: Movie)
}