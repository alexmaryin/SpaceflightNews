package ru.alexmaryin.app.drawer

data class DrawerUiState(
    val opened: Boolean = false,
    val appVersion: String = "",
    val colorTheme: NewsAppTheme
)

enum class NewsAppTheme {
    LIGHT, DARK, SYSTEM
}
