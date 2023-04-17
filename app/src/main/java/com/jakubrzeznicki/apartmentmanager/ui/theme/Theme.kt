package com.jakubrzeznicki.apartmentmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    secondary = White,
    onSecondary = LighterGrey,
    primary = DarkBlue,
    surface = DarkBlue,
    primaryContainer = DarkBlue,
    onPrimary = Black,
    background = LighterGrey,
    error = RedDark
)

private val LightColorScheme = lightColorScheme(
    secondary = LighterGrey,
    onSecondary = LighterGrey,
    primary = DarkBlue,
    surface = DarkBlue,
    primaryContainer = DarkBlue,
    onPrimary = Black,
    background = LighterGrey,
    error = RedDark
)

@Composable
fun ApartmentManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}