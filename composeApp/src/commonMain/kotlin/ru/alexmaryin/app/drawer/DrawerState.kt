package ru.alexmaryin.app.drawer

data class DrawerState(
    val opened: Boolean = false,
    val appVersion: String = "",
    val colorTheme: NewsAppTheme
)

enum class NewsAppTheme {
    LIGHT, DARK, SYSTEM
}
