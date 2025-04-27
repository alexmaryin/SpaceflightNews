package ru.alexmaryin.news.ui.news_list

import ru.alexmaryin.news.domain.models.Article

sealed interface NewsListAction {
    data class OnSearchQueryChange(val query: String): NewsListAction
    data class OnNewsItemClick(val article: Article): NewsListAction
    data class OnTabSelected(val index: Int): NewsListAction
    data class OnScrollChanged(val isAtTop: Boolean) : NewsListAction
    data object OnScrollHandled : NewsListAction
    data object OnBarButtonClicked : NewsListAction
    data object OnRefreshed: NewsListAction
    data object OnSideMenuClick: NewsListAction
}