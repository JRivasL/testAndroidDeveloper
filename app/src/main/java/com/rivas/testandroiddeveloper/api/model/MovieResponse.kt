package com.rivas.testandroiddeveloper.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("results") @Expose val results: List<Movie>,
    @SerializedName("total_pages") @Expose val totalPages: Int,
    @SerializedName("total_results") @Expose val totalResults: Int,
) : Parcelable

@Parcelize
data class Movie(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("adult") @Expose val adult: Boolean,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_title") @Expose val originalTitle: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("release_date") @Expose val releaseDate: String,
    @SerializedName("title") @Expose val title: String,
    @SerializedName("video") @Expose val video: Boolean,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int,
    @SerializedName("genre_ids") @Expose val genreIds: List<Int>
) : Parcelable {
    fun toMovieRoom(page: String): com.rivas.testandroiddeveloper.data.Movie {
        return com.rivas.testandroiddeveloper.data.Movie(
            id,
            adult,
            backdropPath,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            title,
            video,
            voteAverage,
            voteCount,
            genreIds.joinToString(","),
            page
        )
    }
}