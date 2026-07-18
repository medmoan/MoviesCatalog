package com.example.netflixclone.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchHistorySection(
    history: List<String>,
    onSearchClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onClearAll: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Recent Searches",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            TextButton(
                onClick = onClearAll
            ) {
                Text(
                    text = "Clear All",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        history.forEach { query ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSearchClick(query)
                    }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = query,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                IconButton(
                    onClick = {
                        onDeleteClick(query)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider()
        }
    }
}