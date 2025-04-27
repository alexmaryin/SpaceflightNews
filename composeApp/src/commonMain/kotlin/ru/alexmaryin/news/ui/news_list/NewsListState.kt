package ru.alexmaryin.news.ui.news_list

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ru.alexmaryin.core.ui.UiText
import ru.alexmaryin.news.domain.models.Article

data class NewsListState(
    val searchQuery: String = "",
    val articlesFlow: Flow<PagingData<Article>> = emptyFlow(),
    val favouriteArticles: List<Article> = emptyList(),
    val isRefreshing: Boolean = false,
    val isAtTop: Boolean = true,
    val scrollEvent: ScrollEvent? = null,
    val selectedTabIndex: Int = Tabs.ARTICLES_TAB,
    val error: UiText? = null,
)

enum class ScrollEvent { SCROLL_UP }