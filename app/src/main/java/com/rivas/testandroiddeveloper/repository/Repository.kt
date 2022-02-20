package com.rivas.testandroiddeveloper.repository

import android.content.Context
import com.rivas.testandroiddeveloper.R
import com.rivas.testandroiddeveloper.api.model.MovieResponse
import com.rivas.testandroiddeveloper.api.response.ApiErrorResponse
import com.rivas.testandroiddeveloper.api.response.ApiSuccessResponse
import com.rivas.testandroiddeveloper.utils.extensions.toApiException
import com.rivas.testandroiddeveloper.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface Repository {

    sealed class ApiResult<out T : Any> {
        data class Success<out T : Any>(val data: T) : ApiResult<T>()
        data class Error(val exception: Exception) : ApiResult<Nothing>()
    }

    suspend fun getMovies(page: String): ApiResult<MovieResponse>

    class Network @Inject constructor(private val apiService: ApiService) : Repository {

        @Inject
        lateinit var context: Context

        override suspend fun getMovies(page: String) = withContext(Dispatchers.Default) {
            when (val apiResult = apiService.getMoviesByPageAsync(page).await()) {
                is ApiSuccessResponse ->
                    ApiResult.Success(apiResult.data)
                is ApiErrorResponse ->
                    ApiResult.Error(apiResult.errorMessage.toApiException())
                else ->
                    ApiResult.Error(context.getString(R.string.connectivity_error_message).toApiException())
            }
        }
    }
}