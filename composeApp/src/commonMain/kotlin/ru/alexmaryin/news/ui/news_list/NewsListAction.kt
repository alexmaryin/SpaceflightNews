package ru.alexmaryin.news.ui.news_list

import ru.alexmaryin.news.domain.models.Article

sealed interface NewsListAction {
    data class OnSearchQueryChange(val query: String): NewsListAction
    data class OnNewsItemClick(val article: Article): NewsListAction
    data class OnTabSelected(val index: Int): NewsListAction
    data object OnScrollToStart: NewsListAction
    data object OnScrolledUp: NewsListAction
}