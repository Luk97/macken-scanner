package com.nickel.mackenscanner.ui.scanner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nickel.mackenscanner.data.ScanValidationResult
import com.nickel.mackenscanner.ui.theme.AppTheme

@Composable
internal fun ErrorView(result: ScanValidationResult, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = result.message,
            color = AppTheme.colorScheme.onBackground,
            style = AppTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            tint = AppTheme.colorScheme.error,
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        TimerAnimation(barColor = AppTheme.colorScheme.error)
    }

}