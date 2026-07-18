package com.example.netflixclone.settings.domain.repository

import com.example.netflixclone.settings.domain.model.AppSettings
import com.example.netflixclone.settings.domain.model.Theme

interface SettingsRepository {
    suspend fun getSettings(): AppSettings
    suspend fun setLanguage(language: String)
    suspend fun setTheme(theme: Theme)

    suspend fun setIncludeAdult(enabled: Boolean)
}