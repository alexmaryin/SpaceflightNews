package ru.alexmaryin.app.drawer

sealed interface DrawerAction {
    data object AboutClicked: DrawerAction
    data object OpenDrawer: DrawerAction
    data object CloseDrawer: DrawerAction
    data class ChangeTheme(val newTheme: NewsAppTheme) : DrawerAction
}