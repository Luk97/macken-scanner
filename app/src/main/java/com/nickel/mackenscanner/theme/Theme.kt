package com.nickel.mackenscanner.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = AppTypography
    CompositionLocalProvider(
        LocalColorScheme.provides(colorScheme),
        LocalTypography provides(typography),
        content = content
    )
}

internal object AppTheme {
    val colorScheme: MackenScannerColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current
    val typography: MackenScannerTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

}

private val LocalColorScheme = staticCompositionLocalOf<MackenScannerColorScheme> { LightColorScheme }
private val LocalTypography = staticCompositionLocalOf<MackenScannerTypography> { AppTypography }