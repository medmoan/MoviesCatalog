package com.example.netflixclone.movies.presentation.movies

import com.example.netflixclone.movies.domain.model.Movie

data class MovieState(
    val isPagingLoading: Boolean = false,
    val pagingError: String = "",
    val popularPage: Int = 1,
    val popularHasNextPage: Boolean = false,
    val trendingPage: Int = 1,
    val trendingHasNextPage: Boolean = false,
    val selected: MovieType = MovieType.POPULAR,
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isPagingError: Boolean = false,
    val error: String? = null
)
