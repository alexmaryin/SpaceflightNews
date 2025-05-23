package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.domain.HandlePagingItems
import ru.alexmaryin.core.ui.components.SplashText
import ru.alexmaryin.core.ui.toUiText
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.NewsListAction
import ru.alexmaryin.news.ui.news_list.ScrollEvent
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_search_results

@Composable
fun ArticlesPage(
    scrollEvent: ScrollEvent?,
    articles: LazyPagingItems<Article>,
    onAction: (NewsListAction) -> Unit,
) {
    val newsListState = rememberLazyListState()
    val isLoaded = articles.itemSnapshotList.items.isNotEmpty()

    LaunchedEffect(scrollEvent, isLoaded) {
        if (scrollEvent == ScrollEvent.SCROLL_UP && isLoaded) {
            newsListState.animateScrollToItem(0)
            onAction(NewsListAction.OnScrollHandled)
        }
    }

    LaunchedEffect(newsListState) {
        snapshotFlow { newsListState.canScrollBackward }
            .distinctUntilChanged()
            .collect { canScroll -> onAction(NewsListAction.OnScrollChanged(!canScroll)) }
    }

    HandlePagingItems(articles) {
        onRefresh { CircularProgressIndicator() }
        onEmpty { SplashText(stringResource(Res.string.empty_search_results)) }
        onError { error -> SplashText(error.toUiText().asString(), isError = true) }
        onSuccess { items ->
            LazyColumn(
                state = newsListState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                onPagingItems(key = { it.id }) { article ->
                    ArticleItem(
                        article = article,
                        onClick = { onAction(NewsListAction.OnNewsItemClick(article)) },
                        modifier = Modifier.widthIn(max = 800.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                onAppendItem { CircularProgressIndicator(Modifier.padding(6.dp)) }
                onLastItem { EasterEgg() }
            }
        }
    }
}

