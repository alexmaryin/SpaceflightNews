package ru.alexmaryin.core.ui

import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity

@Composable
actual fun rememberScreenSizeInfo(): ScreenSizeInfo {
    val windowsSize = currentWindowSize()
    with(LocalDensity.current) {
        return remember(windowsSize) {
            ScreenSizeInfo(
                widthDp = windowsSize.width.toDp(),
                heightDp = windowsSize.height.toDp()
            )
        }
    }
}