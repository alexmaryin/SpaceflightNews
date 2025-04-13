package ru.alexmaryin.news.ui.news_list

import ru.alexmaryin.core.ui.UiText
import ru.alexmaryin.news.domain.models.Article

data class NewsListState(
    val searchQuery: String = "",
    val searchResult: List<Article> = emptyList(),
    val favouriteArticles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val isScrollToStart: Boolean = false,
    val selectedTabIndex: Int = Tabs.ARTICLES_TAB,
    val error: UiText? = null
)
