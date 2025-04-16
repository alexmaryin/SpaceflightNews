package ru.alexmaryin.news.ui.news_list

import androidx.paging.Pager
import ru.alexmaryin.core.ui.UiText
import ru.alexmaryin.news.domain.models.Article

data class NewsListState(
    val searchQuery: String = "",
    val searchResult: List<Article> = emptyList(),
    val pager: Pager<Int, Article>,
    val favouriteArticles: List<Article> = emptyList(),
    val refresh: Boolean = false,
    val scrollState: ScrollState = ScrollState.SCROLLED_UP,
    val selectedTabIndex: Int = Tabs.ARTICLES_TAB,
    val error: UiText? = null,
)

enum class ScrollState {
    SCROLL_TO_START,
    SCROLLED_UP,
    SCROLLED_DOWN
}
