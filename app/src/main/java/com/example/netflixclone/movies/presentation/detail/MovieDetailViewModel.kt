package com.example.netflixclone.movies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.favorites.domain.repository.FavoriteRepository
import com.example.netflixclone.movies.data.mapper.toMovie
import com.example.netflixclone.movies.domain.repository.MovieRepository
import com.example.netflixclone.core.utils.Respond
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    fun setToggleFavorite(isFavorite: Boolean) {
        val movie = state.value.movieDetail!!.toMovie()
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(movie)
            _state.update {
                it.copy(isFavorite = isFavorite)
            }
        }

    }
    fun getMovieDetail(movieId: Int) {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = movieRepository.getMovieDetail(movieId)
            val isFavorite = favoriteRepository.isFavorite(movieId)
            result.collect { respond ->
                when (respond) {
                    is Respond.Success -> {
                        _state.update {
                            it.copy(
                                isFavorite = isFavorite,
                                isLoading = false,
                                movieDetail = respond.data
                            )
                        }
                    }
                    is Respond.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = respond.message
                            )
                        }
                    }
                    is Respond.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                }
            }
        }
    }
}