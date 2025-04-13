package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.NewsListAction
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_favourite_results

@Composable
fun FavouritesPage(
    favouritesArticles: List<Article>,
    onAction: (NewsListAction) -> Unit
) {
    val favouritesScrollState = rememberLazyListState()

    if (favouritesArticles.isEmpty()) {
        Text(
            text = stringResource(Res.string.empty_favourite_results),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    } else {
        ArticlesList(
            articles = favouritesArticles,
            onArticleClick = { onAction(NewsListAction.OnNewsItemClick(it)) },
            modifier = Modifier.fillMaxSize(),
            scrollState = favouritesScrollState
        )

    }
}