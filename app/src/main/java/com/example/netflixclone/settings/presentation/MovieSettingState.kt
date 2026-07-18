package com.example.netflixclone.settings.presentation

import com.example.netflixclone.settings.domain.model.Theme

data class MovieSettingsState(
    val theme: Theme = Theme.SYSTEM,
    val language: String = "en-US",
    val includeAdult: Boolean = false
)