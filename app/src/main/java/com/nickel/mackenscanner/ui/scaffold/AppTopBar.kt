package com.nickel.mackenscanner.ui.scaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nickel.mackenscanner.ui.theme.AppTheme
import com.nickel.mackenscanner.ui.theme.ThemedPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppTopBar(
    modifier: Modifier = Modifier,
    onInfoClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title =  {
            Text("Wasteside Scanner")
        },
        actions = {
            IconButton(onClick = onInfoClicked) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.9f)
                )
            }
        },
        modifier = modifier
    )
}

@ThemedPreviews
@Composable
private fun AppTopBarPreview() {
    AppTheme {
        AppTopBar()
    }
}