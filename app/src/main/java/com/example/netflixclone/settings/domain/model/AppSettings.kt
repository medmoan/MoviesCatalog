package com.example.netflixclone.settings.domain.model

data class AppSettings(
    val language: String,
    val includeAdult: Boolean,
    val theme: String = Theme.SYSTEM.name
)