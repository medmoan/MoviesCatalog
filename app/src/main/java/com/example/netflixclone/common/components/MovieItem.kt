package com.example.netflixclone.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.netflixclone.movies.domain.model.Movie
import com.example.netflixclone.core.utils.Constants

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit,
    movie: Movie
) {
    val imageUrl = "${Constants.BASE_IMAGE_URL}${movie.posterPath}"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onMovieClick(movie.id) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "⭐ ${movie.rating}"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}