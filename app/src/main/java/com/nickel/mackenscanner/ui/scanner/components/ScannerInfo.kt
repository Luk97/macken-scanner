package com.nickel.mackenscanner.ui.scanner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nickel.mackenscanner.ui.theme.AppTheme

@Composable
internal fun QrCodeSection(
    modifier: Modifier = Modifier,
    onScannerStarted: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Scanne WasteSide Gutschein",
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(32.dp))
        IconButton(
            onClick = onScannerStarted,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(AppTheme.colorScheme.onBackground)
        ) {
            Icon(
                imageVector = Icons.Default.QrCode,
                contentDescription = null,
                tint = AppTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize(0.75f)
            )
        }
    }
}
