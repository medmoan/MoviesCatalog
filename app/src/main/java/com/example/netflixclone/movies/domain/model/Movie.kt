package com.example.netflixclone.movies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val rating: Double,
    val page: Int = 1,
    val isFavorite: Boolean = false
)
