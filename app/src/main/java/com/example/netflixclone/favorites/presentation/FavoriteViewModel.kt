package com.example.netflixclone.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.favorites.domain.repository.FavoriteRepository
import com.example.netflixclone.movies.domain.model.Movie
import com.example.netflixclone.core.utils.Respond
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state = _state.asStateFlow()

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {

            favoriteRepository
                .getFavoriteMovies()
                .collect { result ->
                    when (result) {
                        is Respond.Success -> {
                            _state.update {
                                it.copy(
                                    isEmpty = false,
                                    movies = result.data,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }
                        is Respond.Error -> {
                            _state.update {
                                it.copy(
                                    isEmpty = true,
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                        is Respond.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                    error = null
                                )
                            }
                        }
                    }
                }
        }
    }

    fun removeFavorite(
        movie: Movie
    ) {
        viewModelScope.launch {
            favoriteRepository.removeFromFavorites(movie)
        }
    }
}