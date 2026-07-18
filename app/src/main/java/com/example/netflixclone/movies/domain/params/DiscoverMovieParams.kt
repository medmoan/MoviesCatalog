package com.example.netflixclone.movies.domain.params

data class DiscoverMovieParams(
    val includeAdult: Boolean = false,
    val includeVideo: Boolean = true,
    val language: String = "en-US",
    val page: Int = 1,
    val sortBy: String = "popularity.desc"
)