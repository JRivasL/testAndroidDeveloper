package com.rivas.testandroiddeveloper.api

import com.rivas.testandroiddeveloper.api.model.MovieResponse
import com.rivas.testandroiddeveloper.api.response.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {
    @GET("3/movie/popular?api_key=2b5d48c6bc2a151c9fa18cda98064194")
    fun getMoviesByPageAsync(@Query("page", encoded = true) page: String): Deferred<ApiResponse<MovieResponse>>
}