package ru.alexmaryin.core.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun VerticalGradient() = Brush.verticalGradient(
    if (isSystemInDarkTheme()) {
        listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.secondary
        )
    } else {
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondaryContainer
        )
    }

)

@Composable
fun FavouriteGradient(favourite: Boolean) = Brush.radialGradient(
    colors = if (favourite) listOf(
        Color.Red,
        Color.Transparent
    ) else listOf(
        Color.White,
        Color.Transparent
    )
)
