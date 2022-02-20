package com.rivas.testandroiddeveloper.repository.room.movie

import androidx.lifecycle.LiveData
import com.rivas.testandroiddeveloper.data.Movie
import com.rivas.testandroiddeveloper.data.MovieDao
import javax.inject.Inject


class MovieDataSource@Inject constructor(private var movieDao: MovieDao) : MovieRepository {

    override fun findByPage(page: String): LiveData<List<Movie>> {
        return movieDao.findByPage(page)
    }

    override fun insert(product: Movie?): Long {
        return movieDao.insert(product)
    }

    override fun delete(product: Movie?): Int {
        return movieDao.delete(product)
    }

    override fun update(product: Movie) {
        movieDao.update(product)
    }
}