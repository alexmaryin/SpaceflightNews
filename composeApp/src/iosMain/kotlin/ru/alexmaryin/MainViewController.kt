package ru.alexmaryin

import androidx.compose.ui.window.ComposeUIViewController
import ru.alexmaryin.app.App
import ru.alexmaryin.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }
