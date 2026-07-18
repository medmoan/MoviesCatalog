package com.example.netflixclone.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun MovieItemShimmer() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .shimmer()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(24.dp)
                .shimmer()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .shimmer()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}