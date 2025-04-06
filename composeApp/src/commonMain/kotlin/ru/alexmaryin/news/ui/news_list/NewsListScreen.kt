package ru.alexmaryin.news.ui.news_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.alexmaryin.core.ui.components.SearchBar
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.components.ArticlesPage
import ru.alexmaryin.news.ui.news_list.components.TabsBar

@Composable
fun NewsListScreenRoot(
    viewModel: NewsListViewModel = koinViewModel(),
    onItemClick: (Article) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    NewsListScreen(
        state,
        onAction = { action ->
            when (action) {
                is NewsListAction.OnNewsItemClick -> onItemClick(action.article)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun NewsListScreen(
    state: NewsListState,
    onAction: (NewsListAction) -> Unit
) {
    val pagerState = rememberPagerState { 2 }
    val newsListState = rememberLazyListState()

    LaunchedEffect(state.searchResult) {
        newsListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar(
            query = state.searchQuery,
            onQueryChange = {
                onAction(NewsListAction.OnSearchQueryChange(it))
            },
            modifier = Modifier.widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        TabsBar(state.selectedTabIndex) { index ->
            onAction(NewsListAction.OnTabSelected(index))
        }

        Spacer(Modifier.height(4.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) { pageIndex ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (pageIndex) {
                    Tabs.ARTICLES_TAB -> {
                        ArticlesPage(state, state.error, onAction, newsListState)
                    }

                    Tabs.FAVOURITES_TAB -> {}
                }
            }


        }

    }


}

