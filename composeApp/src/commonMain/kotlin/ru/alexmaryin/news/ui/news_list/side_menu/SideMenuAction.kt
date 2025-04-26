package ru.alexmaryin.news.ui.news_list.side_menu

sealed interface SideMenuAction {
    data object AboutClicked: SideMenuAction
    data object OpenSideMenu: SideMenuAction
    data object CloseSideMenu: SideMenuAction
}