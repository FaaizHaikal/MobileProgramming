package com.example.task3.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccent,
    background = DarkBackground,
)

@Composable
fun Task3Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme, // Apply the dark theme colors
        typography = Typography, // Uses Type.kt for fonts
        content = content
    )
}