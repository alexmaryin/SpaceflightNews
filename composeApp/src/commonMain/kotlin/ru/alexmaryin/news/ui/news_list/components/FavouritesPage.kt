package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.NewsListAction
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_favourite_results

@Composable
fun FavouritesPage(
    isScrollToStart: Boolean,
    favouritesArticles: List<Article>,
    onAction: (NewsListAction) -> Unit
) {
    val favouritesScrollState = rememberLazyListState()

    LaunchedEffect(isScrollToStart) {
        if (isScrollToStart) {
            favouritesScrollState.animateScrollToItem(0)
            onAction(NewsListAction.OnScrolledUp)
        }
    }

    if (favouritesArticles.isEmpty()) {
        Text(
            text = stringResource(Res.string.empty_favourite_results),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = favouritesScrollState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(favouritesArticles) { article ->
                ArticleItem(
                    article = article,
                    onClick = { onAction(NewsListAction.OnNewsItemClick(article)) },
                    modifier = Modifier.widthIn(max = 800.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}