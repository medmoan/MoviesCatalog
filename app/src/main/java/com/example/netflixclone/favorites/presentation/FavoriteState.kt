package com.example.netflixclone.favorites.presentation

import com.example.netflixclone.movies.domain.model.Movie
import com.example.netflixclone.movies.domain.model.MovieResult

data class FavoriteState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false
)