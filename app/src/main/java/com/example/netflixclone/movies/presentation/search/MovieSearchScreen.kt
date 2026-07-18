package com.example.netflixclone.movies.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.netflixclone.common.components.MovieItem
import com.example.netflixclone.common.components.MovieItemShimmer
import com.example.netflixclone.common.components.SearchHistorySection

@Composable
fun MovieSearchScreen(
    navigateToMovieDetail: (Int) -> Unit
) {
    val viewModel = hiltViewModel<MovieSearchViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    var showSearchHistory by remember { mutableStateOf(true) }

    LaunchedEffect(state.query, state.searchHistory.isEmpty()) {
        showSearchHistory = state.query.isEmpty() && state.searchHistory.isNotEmpty()
    }
//    LaunchedEffect(listState.isScrollInProgress) {
//        if (listState.isScrollInProgress) {
//            if (showSearchHistory) showSearchHistory = false
//        } else {
//            if (showSearchHistory.not()) showSearchHistory = true
//        }
//    }
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { index ->
            if (index == state.movies.lastIndex &&
                state.hasNextPage &&
                !state.isPagingLoading
            ) {

                viewModel.loadNextPage()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            value = state.query,
            onValueChange = {
                viewModel.onQueryChange(it)
            },
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    if (showSearchHistory) showSearchHistory = false
                }
            },
            placeholder = {
                Text("Search movies...")
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (state.query.isNotBlank()) {
                    keyboardController?.hide()
                    viewModel.searchMovies()
                }

            }),
            trailingIcon = {
                if (state.query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        )

        Spacer(Modifier.height(16.dp))
        if (showSearchHistory) {
            SearchHistorySection(
                history = state.searchHistory.takeLast(4),
                onSearchClick = {
                    viewModel.onQueryChange(it)
                    viewModel.searchMovies()
                },
                onDeleteClick = {
                    viewModel.onDeleteSearchHistory(it)
                },
                onClearAll = {
                    viewModel.onClearSearchHistory()
                }
            )
        }

        when {
            state.isLoading -> {
                    LazyColumn {
                        items(10) {
                            MovieItemShimmer()
                        }
                 }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.error!!)
                }
            }



            else -> {
                LazyColumn(state = listState) {
                    items(state.movies) { movie ->
                        MovieItem(
                            movie = movie,
                            onMovieClick = navigateToMovieDetail
                        )
                    }

                    if (state.isPagingLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    if (state.isSuccessful && state.movies.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No movies found.")
                            }
                        }
                }
                }
            }
        }
    }
}