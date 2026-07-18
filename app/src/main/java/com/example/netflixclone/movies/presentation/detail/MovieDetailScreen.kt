package com.example.netflixclone.movies.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.netflixclone.core.utils.Constants


@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieId: Int
) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getMovieDetail(movieId)
    }
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("${state.error}")
            }

        }

        state.movieDetail != null -> {

            val movie = state.movieDetail!!

            LazyColumn(
                modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            ) {
                item {

                    AsyncImage(
                        model = "${Constants.DETAIL_IMAGE_URL}${movie.backdropPath}",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = "${Constants.BASE_IMAGE_URL}${movie.posterPath}",
                            contentDescription = movie.title,
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = movie.tagline ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = "⭐ ${movie.voteAverage}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "${movie.runtime} min",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = movie.releaseDate,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = { viewModel.setToggleFavorite(!state.isFavorite) },
                            modifier = Modifier.align(Alignment.Top)
                        ) {
                            Icon(
                                imageVector = if (state.isFavorite) Icons.Rounded.Star else Icons.Rounded.StarBorder,
                                contentDescription = "Favorite this movie",
                                tint = if (state.isFavorite) Color.Yellow else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(4.dp).height(32.dp).width(32.dp).shadow(
                                    elevation = 4.dp,
                                    shape = MaterialTheme.shapes.medium
                                )
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = movie.genres,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = movie.overview,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Status: ${movie.status}",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(32.dp))
                        if (movie.videoKey != null) {
                        val context = LocalContext.current
                        Box(modifier = Modifier.fillMaxWidth()){
                            Button(
                                modifier = Modifier.align(Alignment.Center),
                                onClick = {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            "https://www.youtube.com/watch?v=${movie.videoKey}"
                                        )
                                    )

                                    context.startActivity(intent)
                                }
                            ) {
                                Text(
                                    text = "Watch Trailer",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

