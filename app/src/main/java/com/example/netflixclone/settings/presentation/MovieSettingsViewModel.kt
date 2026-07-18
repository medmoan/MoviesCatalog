package com.example.netflixclone.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.settings.domain.model.AppSettings
import com.example.netflixclone.settings.domain.model.Theme
import com.example.netflixclone.settings.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    private val _state = MutableStateFlow(MovieSettingsState())
    val state = _state.asStateFlow()

    init {
        getSettings()
    }

    private fun getSettings() {
        viewModelScope.launch {
            val appSettings = settingsRepository.getSettings()
            _state.update {
                it.copy(
                    theme = Theme.valueOf(appSettings.theme),
                    language = appSettings.language,
                    includeAdult = appSettings.includeAdult
                )
            }
        }
    }
    fun onThemeChange(theme: Theme) {
        _state.update {
            it.copy(
                theme = theme
            )
        }
        viewModelScope.launch {
            settingsRepository.setTheme(state.value.theme)
        }
    }
    fun onLanguageChange(language: String) {
        _state.update {
            it.copy(
                language = language
            )
        }
        viewModelScope.launch {
            settingsRepository.setLanguage(state.value.language)
        }
    }
    fun onIncludeAdultChange(enabled: Boolean) {
        _state.update {
            it.copy(
                includeAdult = enabled
            )
        }
        viewModelScope.launch {
            settingsRepository.setIncludeAdult(state.value.includeAdult)
        }
    }
}