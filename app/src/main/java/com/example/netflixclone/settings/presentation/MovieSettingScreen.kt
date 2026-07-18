package com.example.netflixclone.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.netflixclone.settings.domain.model.Theme

@Composable
fun MovieSettingsScreen(
    getState: () -> MovieSettingsState,
    onThemeChange: (Theme) -> Unit,
    onIncludeAdultChange: (Boolean) -> Unit,
    onLanguageClick: () -> Unit
) {
    val state = getState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(8.dp)
            )

            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            HorizontalDivider()

            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Column {

                Theme.entries.forEach { theme ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onThemeChange(theme)
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = state.theme == theme,
                            onClick = {
                                onThemeChange(theme)
                            }
                        )

                        Text(
                            text = theme.name,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        item {
            HorizontalDivider()

            Text(
                text = "Language",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            ListItem(
                headlineContent = {
                    Text("English")
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable {
                    onLanguageClick()
                }
            )
        }

        item {
            HorizontalDivider()

            ListItem(
                headlineContent = {
                    Text("Include Adult Content")
                },
                trailingContent = {
                    Switch(
                        checked = state.includeAdult,
                        onCheckedChange = onIncludeAdultChange
                    )
                }
            )
        }
    }
}