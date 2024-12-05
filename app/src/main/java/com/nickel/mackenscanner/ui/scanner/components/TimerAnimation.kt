package com.nickel.mackenscanner.ui.scanner.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nickel.mackenscanner.ui.scanner.ScannerScreenViewModel.Companion.RESULT_VISIBLE_MS
import com.nickel.mackenscanner.ui.theme.AppTheme

@Composable
fun TimerAnimation(barColor: Color, modifier: Modifier = Modifier) {
    val progress = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = RESULT_VISIBLE_MS.toInt()
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .padding(horizontal = 32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(AppTheme.colorScheme.surface),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.value)
                .clip(RoundedCornerShape(8.dp))
                .background(barColor)
        )
    }
}