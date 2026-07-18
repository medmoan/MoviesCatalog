package com.example.netflixclone.favorites.presentation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.netflixclone.common.components.MovieItem

@Composable
fun FavoriteMoviesScreen(
    navigateBack: () -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
    navigateToSettings: () -> Unit
) {
    val viewModel = hiltViewModel<FavoriteViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()


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

                    IconButton(
                        onClick = {
                            navigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                    Text(
                        text = "Favorite Movies",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error!!,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                state.isEmpty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No favorite movies found.",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.movies) { movie ->
                            MovieItem(
                                movie = movie,
                                onMovieClick = navigateToMovieDetail
                            )
                        }

                    }
                }
            }
        }
    }
}