package com.example.netflixclone.movies.presentation.movies


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.netflixclone.common.components.MovieItem
import com.example.netflixclone.common.components.MovieItemShimmer

@Composable
fun MoviesScreen(
    navigateToMovieDetail: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToFavorite: () -> Unit
) {
    val viewModel = hiltViewModel<MoviesViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listStatePopular = rememberLazyListState()
    val listStateTrending = rememberLazyListState()
    var menuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(listStatePopular) {
        snapshotFlow {
            listStatePopular.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { index ->

            if (index == state.movies.lastIndex &&
                state.popularHasNextPage &&
                !state.isPagingLoading
            ) {

                viewModel.loadNextPage(MovieType.POPULAR)
            }

        }
    }
    LaunchedEffect(listStateTrending) {
        snapshotFlow {
            listStateTrending.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { index ->

            if (index == state.movies.lastIndex &&
                state.trendingHasNextPage &&
                !state.isPagingLoading
            ) {

                viewModel.loadNextPage(MovieType.TRENDING)
            }

        }
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1.5f)
                    ) {

                        FilterChip(
                            modifier = Modifier.height(32.dp),
                            selected = state.selected == MovieType.POPULAR,
                            onClick = {
                                viewModel.onPopularClick()
                            },
                            label = {
                                Text(
                                    text = MovieType.POPULAR.value,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        FilterChip(
                            modifier = Modifier.height(32.dp),
                            selected = state.selected == MovieType.TRENDING,
                            onClick = {
                                viewModel.onTrendingClick()
                            },
                            label = {
                                Text(
                                    text = MovieType.TRENDING.value,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                navigateToSearch()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        IconButton(
                            onClick = {
                                navigateToSettings()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.weight(0.5f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(
                            onClick = {
                                menuExpanded = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = null
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = {
                                menuExpanded = false
                            }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Favorites") },
                                onClick = {
                                    menuExpanded = false
                                    navigateToFavorite()
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

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
                        Text(text = state.error!!)
                    }
                }

                else -> {
                    LazyColumn(
                        state = if(state.selected == MovieType.POPULAR) listStatePopular else listStateTrending,
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                    }
                }
            }
        }
    }
}