package com.example.netflixclone.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.netflixclone.favorites.presentation.FavoriteMoviesScreen
import com.example.netflixclone.movies.presentation.detail.MovieDetailScreen
import com.example.netflixclone.movies.presentation.search.MovieSearchScreen
import com.example.netflixclone.movies.presentation.movies.MoviesScreen
import com.example.netflixclone.settings.domain.model.Theme
import com.example.netflixclone.settings.presentation.MovieSettingsScreen
import com.example.netflixclone.settings.presentation.MovieSettingsViewModel
import com.example.netflixclone.ui.theme.NetflixCloneTheme
import kotlinx.serialization.Serializable

@Serializable
data object Movies
@Serializable
data class MovieDetail(val movieId: Int)
@Serializable
data object MovieSearch
@Serializable
data object MovieSettings
@Serializable
data object MovieFavorites
@Composable
fun AppNavigation() {
    val settingsViewModel = hiltViewModel<MovieSettingsViewModel>()
    val state by settingsViewModel.state.collectAsStateWithLifecycle()

    val darkTheme = when (state.theme) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.LIGHT -> false
        Theme.DARK -> true
    }
    val navController = rememberNavController()
    NetflixCloneTheme(
        darkTheme = darkTheme
    ) {
        NavHost(
            navController = navController,
            startDestination = Movies
        ) {
            composable<Movies> {
                MoviesScreen(
                    navigateToMovieDetail = { movieId ->
                        navController.navigate(MovieDetail(movieId))
                    },
                    navigateToSearch = {
                        navController.navigate(MovieSearch)
                    },
                    navigateToSettings = {
                        navController.navigate(MovieSettings)
                    },
                    navigateToFavorite = {
                        navController.navigate(MovieFavorites)
                    }
                )
            }
            composable<MovieDetail> { backStackEntry ->

                val movieId = backStackEntry.arguments?.getInt("movieId")!!
                MovieDetailScreen(movieId = movieId)
            }
            composable<MovieSearch> {
                MovieSearchScreen{
                    navController.navigate(MovieDetail(it))
                }
            }
            composable<MovieSettings> {
                MovieSettingsScreen(
                    getState = { state },
                    onThemeChange = settingsViewModel::onThemeChange,
                    onIncludeAdultChange = settingsViewModel::onIncludeAdultChange,
                    onLanguageClick = {}
                )
            }
            composable<MovieFavorites> {
                FavoriteMoviesScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToMovieDetail = { movieId ->
                        navController.navigate(MovieDetail(movieId))
                    },
                    navigateToSettings = {
                        navController.navigate(MovieSettings)
                    }
                )
            }
        }
    }

}