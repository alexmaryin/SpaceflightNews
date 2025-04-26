package ru.alexmaryin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import ru.alexmaryin.app.App
import ru.alexmaryin.di.initKoin
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.icon
import java.awt.*

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        println(e.message)
        println(e.stackTraceToString())
        Dialog(Frame(), e.message ?: "Error").apply {
            layout = FlowLayout()
            val label = Label(e.message)
            add(label)
            val button = Button("OK").apply {
                addActionListener { dispose() }
            }
            add(button)
            setSize(300,300)
            isVisible = true
        }
    }



    initKoin()
    application {
        val icon = painterResource(Res.drawable.icon)
        Window(
            onCloseRequest = ::exitApplication,
            title = "SpaceflightNews",
            icon = icon
        ) {
            App()
        }
    }
}