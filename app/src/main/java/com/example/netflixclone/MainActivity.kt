package com.example.netflixclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.netflixclone.navigation.AppNavigation
import com.example.netflixclone.settings.domain.model.Theme
import com.example.netflixclone.settings.presentation.MovieSettingsViewModel
import com.example.netflixclone.ui.theme.NetflixCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}

