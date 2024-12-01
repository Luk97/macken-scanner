package com.nickel.mackenscanner.theme

import androidx.compose.ui.graphics.Color

internal interface MackenScannerColorScheme {
    val primary: Color
    val onPrimary: Color
    val secondary: Color
    val background: Color
    val onBackground: Color
    val surface: Color
    val surfaceVariant: Color
    val surfaceContainer: Color
    val onSurface: Color
    val onSurfaceVariant: Color
    val border: Color
}

internal object LightColorScheme: MackenScannerColorScheme {
    override val primary: Color = Pink
    override val onPrimary: Color = Black
    override val secondary: Color = Purple
    override val background: Color = White
    override val onBackground: Color = DarkGrey
    override val surface: Color = White
    override val surfaceVariant: Color = White.copy(alpha = 0.5f)
    override val surfaceContainer: Color = White
    override val onSurface: Color = DarkGrey
    override val onSurfaceVariant: Color = DarkGrey.copy(alpha = 0.5f)
    override val border: Color = DarkGrey
}

internal object DarkColorScheme: MackenScannerColorScheme {
    override val primary: Color = Pink
    override val onPrimary: Color = Black
    override val secondary: Color = Purple
    override val background: Color = DarkGrey
    override val onBackground: Color = White
    override val surface: Color = MediumGrey
    override val surfaceVariant: Color = MediumGrey.copy(alpha = 0.5f)
    override val surfaceContainer: Color = MediumGrey
    override val onSurface: Color = White
    override val onSurfaceVariant: Color = White.copy(alpha = 0.5f)
    override val border: Color = Black
}