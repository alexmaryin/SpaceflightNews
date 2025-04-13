package ru.alexmaryin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.alexmaryin.app.App
import ru.alexmaryin.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SpaceflightNews",
        ) {
            App() // use bool in parameter for switch dark theme on/off for debug
        }
    }
}