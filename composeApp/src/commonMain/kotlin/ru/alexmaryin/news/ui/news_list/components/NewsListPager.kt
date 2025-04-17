package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import ru.alexmaryin.news.ui.news_list.NewsListAction
import ru.alexmaryin.news.ui.news_list.NewsListState
import ru.alexmaryin.news.ui.news_list.ScrollState
import ru.alexmaryin.news.ui.news_list.Tabs

@Composable
fun ColumnScope.NewsListPager(
    state: NewsListState,
    onAction: (NewsListAction) -> Unit
) {

    val pagerState = rememberPagerState(state.selectedTabIndex) { Tabs.COUNT }
    val articles = state.articlesFlow.collectAsLazyPagingItems()
    if (state.refresh) {
        articles.refresh()
        onAction(NewsListAction.OnRefreshed)
    }

    // pager following to selected tab
    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    // selection of tab following to the page
    LaunchedEffect(pagerState.currentPage) {
        onAction(NewsListAction.OnTabSelected(pagerState.currentPage))
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize().weight(1f)
    ) { pageIndex ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceBright)
                .padding(top = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            val scrollToStart = state.scrollState == ScrollState.SCROLL_TO_START
            when (pageIndex) {
                Tabs.ARTICLES_TAB -> {
                    ArticlesPage(
                        isScrollToStart = scrollToStart,
                        articles = articles,
                        onAction = onAction
                    )
                }

                Tabs.FAVOURITES_TAB -> {
                    FavouritesPage(
                        isScrollToStart = scrollToStart,
                        favouritesArticles = state.favouriteArticles,
                        onAction = onAction
                    )
                }
            }
        }
    }
}

