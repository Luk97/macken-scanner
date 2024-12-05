package com.nickel.mackenscanner.ui.scanner.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nickel.mackenscanner.ui.theme.AppTheme

@Composable
internal fun LoadingView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(128.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(128.dp),
            strokeWidth = 8.dp,
            color = AppTheme.colorScheme.onBackground
        )
    }
}