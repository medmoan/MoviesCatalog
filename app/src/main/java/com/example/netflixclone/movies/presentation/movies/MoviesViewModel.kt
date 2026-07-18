package com.example.netflixclone.movies.presentation.movies


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.movies.domain.repository.MovieRepository
import com.example.netflixclone.core.utils.Respond
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
    ): ViewModel() {
        private val _state = MutableStateFlow(MovieState())
        val state = _state.asStateFlow()


    init {
        getPopularMovies()
    }
    fun loadNextPage(type: MovieType) {
        when(type) {
            MovieType.POPULAR -> {
                Log.d("MoviesViewModel", "Loading Popular next page: ${state.value.popularPage + 1}")
                if (!state.value.popularHasNextPage || state.value.isPagingLoading) return
                getPopularMovies(
                    page = state.value.popularPage + 1
                )
            }
            MovieType.TRENDING -> {
                Log.d("MoviesViewModel", "Loading Trending next page: ${state.value.trendingPage + 1}")
                if (!state.value.trendingHasNextPage || state.value.isPagingLoading) return
                getTrendingMovies(
                    page = state.value.trendingPage + 1
                )
            }
        }
    }
    fun onPopularClick() {
        if (state.value.selected == MovieType.POPULAR) return
        _state.update {
            it.copy(
                selected = MovieType.POPULAR,
                movies = emptyList(),
                isLoading = true
            )
        }
        getPopularMovies()
    }

    fun onTrendingClick() {
        if (state.value.selected == MovieType.TRENDING) return
        _state.update {
            it.copy(
                selected = MovieType.TRENDING,
                movies = emptyList(),
                isLoading = true
            )
        }
        getTrendingMovies()

    }
    private fun getTrendingMovies(page: Int = 1) {
        viewModelScope.launch {
            val moviesRes = movieRepository.fetchTrendingMovie("day", page)
            moviesRes.collect { result ->
                when(result) {
                    is Respond.Error -> {
                        _state.update {
                            it.copy(
                                error = if (page == 1) result.message else it.error,
                                isPagingError = page > 1,
                                pagingError = if (page > 1) result.message else it.pagingError,
                                isLoading = false,
                                isPagingLoading = false
                            )
                        }
                    }
                    is Respond.Loading -> {
                            _state.update {
                                it.copy(
                                    isPagingLoading = page > 1,
                                    //isLoading = page == 1
                                )
                            }
                        }
                    is Respond.Success -> {
                        _state.update {
                            it.copy(
                                trendingPage = page,
                                trendingHasNextPage = result.data.page < result.data.totalPages,
                                isLoading = false,
                                isPagingLoading = false,
                                movies = if (page == 1) result.data.movies else it.movies + result.data.movies
                            )
                        }
                    }
                }

            }

        }
    }

    private fun getPopularMovies(page: Int = 1) {

        viewModelScope.launch {
            val moviesRes = movieRepository.fetchDiscoverMovie(page)
            moviesRes.collect { result ->
                when(result) {
                    is Respond.Error -> {
                        _state.update {
                            it.copy(
                                error = if (page == 1) result.message else it.error,
                                isPagingError = page > 1,
                                pagingError = if (page > 1) result.message else it.pagingError,
                                isLoading = false,
                                isPagingLoading = false
                            )
                        }
                    }
                    Respond.Loading -> {
                        _state.update {
                            it.copy(
                                isPagingLoading = page > 1,
                                //isLoading = page == 1
                            )
                        }
                    }
                    is Respond.Success -> {
                        _state.update {
                            it.copy(
                                popularPage = page,
                                popularHasNextPage = result.data.page < result.data.totalPages,
                                isLoading = false,
                                isPagingLoading = false,
                                movies = if (page == 1) result.data.movies else it.movies + result.data.movies
                            )
                        }
                    }
                }
            }
        }
    }
}