package ru.alexmaryin.app.drawer

sealed interface DrawerAction {
    data object AboutClicked: DrawerAction
    data object OpenDrawer: DrawerAction
    data object CloseDrawer: DrawerAction
}