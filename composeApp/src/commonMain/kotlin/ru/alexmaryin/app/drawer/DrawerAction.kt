package ru.alexmaryin.app.drawer

sealed interface DrawerAction {
    data object AboutClicked: DrawerAction
    data class SideMenuChange(val isOpen: Boolean) : DrawerAction
    data class ChangeTheme(val newTheme: NewsAppTheme) : DrawerAction
}