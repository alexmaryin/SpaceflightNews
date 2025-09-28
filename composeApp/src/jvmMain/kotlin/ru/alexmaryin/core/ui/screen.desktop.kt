package ru.alexmaryin.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberScreenSizeInfo(): ScreenSizeInfo {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current.density
    return remember(containerSize, density) {
        ScreenSizeInfo(
            widthDp = (containerSize.width * density).dp,
            heightDp = (containerSize.height * density).dp
        )
    }
}