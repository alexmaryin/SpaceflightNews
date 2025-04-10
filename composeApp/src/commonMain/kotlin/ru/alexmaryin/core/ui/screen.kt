package ru.alexmaryin.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class ScreenSizeInfo(
    val widthDp: Dp,
    val heightDp: Dp
)

fun ScreenSizeInfo.isCompact() = widthDp <= 600.dp

@Composable
expect fun rememberScreenSizeInfo(): ScreenSizeInfo
