package com.example.netflixclone.movies.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.core.domain.repository.SearchHistoryRepository
import com.example.netflixclone.movies.domain.repository.MovieRepository
import com.example.netflixclone.core.utils.Respond
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val searchHistoryRepository: SearchHistoryRepository
): ViewModel() {
    private val _state = MutableStateFlow(MovieSearchState())
    val state = _state.asStateFlow()
    private var _queries = mutableListOf<String>()
    init {
        getSearchHistory()
    }
    private suspend fun preventDuplicateSearches(insertBlock: suspend(String) -> Unit) {
        val query = state.value.query
        val queries = state.value.searchHistory


        if (queries.none { it == query }) {
            insertBlock(query)
        }
    }
    private fun insertSearchHistory(query: String) {
        viewModelScope.launch {
            searchHistoryRepository.insert(query)
        }
    }
    fun onDeleteSearchHistory(query: String) {
        viewModelScope.launch {
            searchHistoryRepository.delete(query)
        }
    }

    fun onClearSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.clear()
        }
    }

    private fun getSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.getSearchHistory().collect { result ->
                when(result) {
                    is Respond.Error -> Unit
                    is Respond.Loading -> Unit
                    is Respond.Success -> {
                        _state.update {
                            it.copy(
                                searchHistory = result.data.map { search -> search.query }
                            )
                        }
                    }
                }
            }
        }
    }
    fun onQueryChange(query: String) {
        _state.update {
            it.copy(
                query = query
            )
        }

    }
    fun loadNextPage() {
        if (!state.value.hasNextPage) return

        searchMovies(
            page = state.value.page + 1
        )
    }
    fun searchMovies(page: Int = 1) {
        _queries.add(state.value.query)
        viewModelScope.launch {
            preventDuplicateSearches { query ->
                insertSearchHistory(query)
            }
            movieRepository.fetchSearchMovie(
                query = state.value.query,
                page = page
            ).collect { result ->
                when(result) {
                    is Respond.Error -> {
                        _state.update {
                            it.copy(
                                isSuccessful = false,
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
                                isLoading = page == 1
                            )
                        }
                    }
                    is Respond.Success -> {
                        _state.update {
                            it.copy(
                                isSuccessful = true,
                                page = page,
                                hasNextPage = result.data.page < result.data.totalPages,
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