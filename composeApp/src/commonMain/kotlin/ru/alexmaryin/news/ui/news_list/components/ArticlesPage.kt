package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.UiText
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.NewsListAction
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_search_results

@Composable
fun ArticlesPage(
    isLoading: Boolean,
    searchResult: List<Article>,
    error: UiText?,
    onAction: (NewsListAction) -> Unit,
) {
    val newsListState = rememberLazyListState()

    LaunchedEffect(searchResult) {
        newsListState.animateScrollToItem(0)
    }

    when {
        isLoading -> CircularProgressIndicator()
        error != null -> Text(
            text = error.asString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
        searchResult.isEmpty() -> Text(
            text = stringResource(Res.string.empty_search_results),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        else -> ArticlesList(
            articles = searchResult,
            onArticleClick = { onAction(NewsListAction.OnNewsItemClick(it)) },
            modifier = Modifier.fillMaxSize(),
            scrollState = newsListState
        )
    }
}