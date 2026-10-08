package com.rafaelildefonso.diariotreinos.userinterface

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CoresHabitTracker = lightColorScheme(
    primary = Color(0xFF2D6A4F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD8F0E1),
    onPrimaryContainer = Color(0xFF0B3D25),
    secondary = Color(0xFF52796F),
    background = Color(0xFFF8FAF9),
    surface = Color.White,
    surfaceVariant = Color(0xFFEFF3F1),
)

@Composable
fun HabitTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = CoresHabitTracker, content = content)
}