package com.example.netflixclone.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {

    val LANGUAGE = stringPreferencesKey("language")

    val INCLUDE_ADULT = booleanPreferencesKey("include_adult")

    val THEME = stringPreferencesKey("theme")
}