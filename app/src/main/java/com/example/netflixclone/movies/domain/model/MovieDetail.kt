package com.example.netflixclone.movies.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val budget: Int,
    val genres: String,
    val productionCompanies: String,
    val status: String,
    val tagline: String,
    val posterPath: String,
    val backdropPath: String,
    val videoKey: String? = null,
    val cachedAt: Long = System.currentTimeMillis()
)