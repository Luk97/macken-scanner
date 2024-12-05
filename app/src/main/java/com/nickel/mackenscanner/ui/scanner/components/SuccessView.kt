package com.nickel.mackenscanner.ui.scanner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nickel.mackenscanner.ui.theme.AppTheme

@Composable
internal fun SuccessView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "WasteSide QrCode korrekt",
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(32.dp))
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = AppTheme.colorScheme.success,
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        TimerAnimation(barColor = AppTheme.colorScheme.success)
    }
}