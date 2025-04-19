package ru.alexmaryin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import ru.alexmaryin.app.App
import ru.alexmaryin.di.initKoin
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.icon

fun main() {
    initKoin()
    application {
        val icon = painterResource(Res.drawable.icon)
        Window(
            onCloseRequest = ::exitApplication,
            title = "SpaceflightNews",
            icon = icon
        ) {
            App() // use bool in parameter for switch dark theme on/off for debug
        }
    }
}