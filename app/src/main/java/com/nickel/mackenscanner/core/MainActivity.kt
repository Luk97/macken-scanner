package com.nickel.mackenscanner.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nickel.mackenscanner.theme.MackenScannerTheme
import com.nickel.mackenscanner.ui.scaffold.MainScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MackenScannerTheme {
                MainScaffold()
            }
        }
    }
}