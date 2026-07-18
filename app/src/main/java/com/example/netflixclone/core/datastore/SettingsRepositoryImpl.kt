package com.example.netflixclone.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.netflixclone.settings.domain.model.AppSettings
import com.example.netflixclone.settings.domain.model.Theme
import com.example.netflixclone.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    override suspend fun getSettings(): AppSettings {
        val prefs = dataStore.data.first()

        return AppSettings(
            theme = prefs[PreferencesKeys.THEME] ?: Theme.SYSTEM.name,
            language = prefs[PreferencesKeys.LANGUAGE] ?: "en-US",
            includeAdult = prefs[PreferencesKeys.INCLUDE_ADULT] ?: false
        )
    }
    override suspend fun setLanguage(language: String) {
        dataStore.edit {
            it[PreferencesKeys.LANGUAGE] = language
        }
    }

    override suspend fun setTheme(theme: Theme) {
        dataStore.edit {
            it[PreferencesKeys.THEME] = theme.name
        }
    }

    override suspend fun setIncludeAdult(enabled: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.INCLUDE_ADULT] = enabled
        }
    }
}