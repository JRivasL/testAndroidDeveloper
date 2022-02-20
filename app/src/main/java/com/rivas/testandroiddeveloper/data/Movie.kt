package com.rivas.testandroiddeveloper.data

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "adult") @Nullable val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") @Nullable val backdropPath: String?,
    @ColumnInfo(name = "original_language") @Nullable val originalLanguage: String?,
    @ColumnInfo(name = "original_title") @Nullable val originalTitle: String?,
    @ColumnInfo(name = "overview") @Nullable val overview: String?,
    @ColumnInfo(name = "popularity") @Nullable val popularity: Double,
    @ColumnInfo(name = "poster_path") @Nullable val posterPath: String?,
    @ColumnInfo(name = "release_date") @Nullable var releaseDate: String?,
    @ColumnInfo(name = "title") @Nullable val title: String?,
    @ColumnInfo(name = "video") @Nullable val video: Boolean,
    @ColumnInfo(name = "vote_average") @Nullable val voteAverage: Double,
    @ColumnInfo(name = "vote_count") @Nullable val voteCount: Int,
    @ColumnInfo(name = "genre_ids") @Nullable val genreIds: String?,
    @ColumnInfo(name = "page") @Nullable val page: String?,
)