package com.example.netflixclone.movies.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_detail")
class MovieDetailEntity(

    @PrimaryKey
    val id: Int,

    val title: String,
    val originalTitle: String,

    val overview: String,

    val releaseDate: String,

    val runtime: Int,

    val voteAverage: Double,
    val voteCount: Int,

    val popularity: Double,

    val genres: String,

    val status: String,
    val budget: Int,
    val productionCompanies: String,
    val tagline: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val cachedAt: Long
)