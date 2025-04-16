package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import app.cash.paging.compose.collectAsLazyPagingItems
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.NewsListAction
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_search_results

@Composable
fun ArticlesPage(
    refresh: Boolean,
    isScrollToStart: Boolean,
    pager: Pager<Int, Article>,
    onAction: (NewsListAction) -> Unit,
) {
    val newsListState = rememberLazyListState()
    val articles = pager.flow.collectAsLazyPagingItems()

    LaunchedEffect(isScrollToStart) {
        if (isScrollToStart) {
            newsListState.animateScrollToItem(0)
        }
    }

    if (refresh) {
        articles.refresh()
        onAction(NewsListAction.OnRefreshed)
    }

    LaunchedEffect(newsListState.canScrollBackward) {
        if (newsListState.canScrollBackward)
            onAction(NewsListAction.OnScrollDown)
        else onAction(NewsListAction.OnScrolledUp)
    }

    LazyColumn(
        state = newsListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (articles.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = stringResource(Res.string.empty_search_results),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        items(count = articles.itemCount) { index ->
            val article = articles[index]
            article?.let {
                ArticleItem(
                    article = it,
                    onClick = { onAction(NewsListAction.OnNewsItemClick(it)) },
                    modifier = Modifier.widthIn(max = 800.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        if (articles.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator()
            }
        }
    }
}
