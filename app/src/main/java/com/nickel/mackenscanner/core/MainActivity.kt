package com.nickel.mackenscanner.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.nickel.mackenscanner.ui.scanner.ScannerScreen
import com.nickel.mackenscanner.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(
                    containerColor = AppTheme.colorScheme.surface,
                    contentColor = AppTheme.colorScheme.onSurface
                ) { innerPadding ->
                    ScannerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}