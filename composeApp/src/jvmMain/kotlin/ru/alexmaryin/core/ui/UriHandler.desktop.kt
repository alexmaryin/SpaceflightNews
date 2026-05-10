package ru.alexmaryin.core.ui

actual class UriHandler {
    actual fun openUrl(url: String) {
        java.awt.Desktop.getDesktop().browse(java.net.URI(url))
    }
}