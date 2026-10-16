package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ZenLavender,
    onPrimary = ZenDeepPurple,
    primaryContainer = ZenDeepPurple,
    onPrimaryContainer = Color.White,
    secondary = TurboAmber,
    onSecondary = Color.Black,
    tertiary = SkyBlue,
    background = ZenCosmicNavy,
    surface = Color(0xFF232344),
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryIndigo,
    onPrimary = Color.White,
    primaryContainer = ZenLilacLight,
    onPrimaryContainer = ZenDeepPurple,
    secondary = TurboOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = Color(0xFFE65100),
    tertiary = SecondaryTeal,
    onTertiary = Color.White,
    background = SurfaceCream,
    surface = SurfaceCardLight,
    surfaceVariant = Color(0xFFF0EDF5),
    onBackground = TextDark,
    onSurface = TextDark,
    onSurfaceVariant = TextMuted
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
