package com.rivas.testandroiddeveloper.api.response

import com.rivas.testandroiddeveloper.utils.extensions.isJson
import com.rivas.testandroiddeveloper.utils.extensions.toJson
import retrofit2.Response

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> = ApiErrorResponse(
            error.message
                ?: "unknown error"
        )

        fun <T> create(response: Response<T>): ApiResponse<T> =
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(data = body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }

                if (errorMsg.isJson()) {
                    val json = errorMsg.toJson()
                    ApiErrorResponse(
                        json.get("status_message").asString
                    )
                } else {
                    ApiErrorResponse("unknown error")
                }
            }
    }
}

data class ApiSuccessResponse<T>(val data: T) : ApiResponse<T>()
data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
class ApiEmptyResponse<T> : ApiResponse<T>()