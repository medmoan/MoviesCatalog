package com.example.netflixclone.movies.presentation.search

import com.example.netflixclone.movies.domain.model.Movie

data class MovieSearchState(
    val searchHistory: List<String> = emptyList(),
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val page: Int = 1,
    val hasNextPage: Boolean = true,
    val isLoading: Boolean = false,
    val isPagingLoading: Boolean = false,
    val error: String? = null,
    val isSuccessful: Boolean = false,
    val isPagingError: Boolean = false,
    val pagingError: String = ""
)