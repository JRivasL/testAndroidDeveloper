package com.rivas.testandroiddeveloper.ui.main.movies

import androidx.lifecycle.MutableLiveData
import com.rivas.testandroiddeveloper.api.model.Movie
import com.rivas.testandroiddeveloper.api.model.MovieResponse
import com.rivas.testandroiddeveloper.core.CoroutinesViewModel
import com.rivas.testandroiddeveloper.repository.Repository
import com.rivas.testandroiddeveloper.repository.room.TestDataBase
import com.rivas.testandroiddeveloper.repository.room.movie.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: Repository,
    private val movieRepository: MovieRepository,
    private val database: TestDataBase
) : CoroutinesViewModel() {

    var page = 1
    val movies = MutableLiveData<List<com.rivas.testandroiddeveloper.data.Movie>>()
    val addObserver = MutableLiveData<Boolean>()

    private fun getMovies(page: String) = uiScope.launch {
        _loading.value = true
        when (val movies = repository.getMovies(page)) {
            is Repository.ApiResult.Success<MovieResponse> -> {
                processMovies(movies.data)
            }
            is Repository.ApiResult.Error -> {
                _error.value = movies.exception
            }
        }
        _loading.value = false
    }

    private fun processMovies(data: MovieResponse) {
        if (data.results.isNotEmpty()) {
            page = data.page
            data.results.forEach {
                createMovie(it, data.results.last() == it)
            }
        }
    }

    fun validateLocalData() {
        var moviesAux: List<com.rivas.testandroiddeveloper.data.Movie>
        CoroutineScope(Dispatchers.IO).launch {
            moviesAux = database.movieDao.findByPageLocal(page.toString())
            if (moviesAux.isEmpty()) {
                loadData()
            } else {
                movies.postValue(moviesAux)
            }
        }

    }

    private fun createMovie(movie: Movie, last: Boolean) {
        uiScope.launch(Dispatchers.IO) {
            if (database.movieDao.findById(movie.id).isEmpty()) {
                movieRepository.insert(
                    movie.toMovieRoom(page.toString())
                )
            }
            if(last){
                addObserver.postValue(true)
            }
        }
    }

    private fun loadData() {
        getMovies(page.toString())
    }

    fun nextPage() {
        page++
        validateLocalData()
    }

}