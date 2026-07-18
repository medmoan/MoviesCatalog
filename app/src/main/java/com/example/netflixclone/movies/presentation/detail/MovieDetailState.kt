package com.example.netflixclone.movies.presentation.detail

import com.example.netflixclone.movies.domain.model.MovieDetail

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)