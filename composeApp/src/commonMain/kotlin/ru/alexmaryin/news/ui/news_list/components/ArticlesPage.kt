package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.UiText
import ru.alexmaryin.news.ui.news_list.NewsListAction
import ru.alexmaryin.news.ui.news_list.NewsListState
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_search_results

@Composable
fun ArticlesPage(
    state: NewsListState,
    error: UiText?,
    onAction: (NewsListAction) -> Unit,
    newsListState: LazyListState
) {
    when {
        state.isLoading -> CircularProgressIndicator()
        state.error != null -> Text(
            text = error.asString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )

        state.searchResult.isEmpty() -> Text(
            text = stringResource(Res.string.empty_search_results),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        else -> ArticlesList(
            articles = state.searchResult,
            onArticleClick = { onAction(NewsListAction.OnNewsItemClick(it)) },
            modifier = Modifier.fillMaxSize(),
            scrollState = newsListState
        )
    }
}