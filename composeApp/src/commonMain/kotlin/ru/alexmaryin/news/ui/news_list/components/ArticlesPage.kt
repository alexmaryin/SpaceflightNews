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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.domain.onEmpty
import ru.alexmaryin.core.domain.onError
import ru.alexmaryin.core.domain.onRefresh
import ru.alexmaryin.core.ui.components.BodyText
import ru.alexmaryin.core.ui.toUiText
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.NewsListAction
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_search_results

@Composable
fun ArticlesPage(
    isScrollToStart: Boolean,
    articles: LazyPagingItems<Article>,
    onAction: (NewsListAction) -> Unit,
) {
    val newsListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    if (isScrollToStart) scope.launch {
        newsListState.animateScrollToItem(0)
    }

    LaunchedEffect(newsListState.canScrollBackward) {
        if (newsListState.canScrollBackward)
            onAction(NewsListAction.OnScrollDown)
        else onAction(NewsListAction.OnScrolledUp)
    }

    articles.onRefresh { CircularProgressIndicator() }
        ?.onEmpty { BodyText(stringResource(Res.string.empty_search_results)) }
        ?.onError { error -> BodyText(error.toUiText().asString(), isError = true) }
        ?.let { page ->
            LazyColumn(
                state = newsListState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(count = page.itemCount) { index ->
                    val article = page[index]
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
                if (articles.loadState.append == LoadState.Loading) item {
                    CircularProgressIndicator(Modifier.padding(6.dp))
                }
            }
        }
}

