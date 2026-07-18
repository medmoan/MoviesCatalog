package com.example.netflixclone.movies.domain.model

data class MovieResult(
    val page: Int = 1,
    val movies: List<Movie>,
    val totalPages: Int = 1,
    val totalResults: Int = 1
)
