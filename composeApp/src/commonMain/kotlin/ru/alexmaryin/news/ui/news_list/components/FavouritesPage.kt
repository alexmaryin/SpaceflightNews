package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.SplashText
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.ui.news_list.NewsListAction
import ru.alexmaryin.news.ui.news_list.ScrollEvent
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.empty_favourite_results

@Composable
fun FavouritesPage(
    scrollEvent: ScrollEvent?,
    favouritesArticles: List<Article>,
    onAction: (NewsListAction) -> Unit
) {
    val favouritesScrollState = rememberLazyListState()

    LaunchedEffect(scrollEvent) {
        if (scrollEvent == ScrollEvent.SCROLL_UP) {
            favouritesScrollState.animateScrollToItem(0)
            onAction(NewsListAction.OnScrollHandled)
        }
    }

    LaunchedEffect(favouritesScrollState) {
        snapshotFlow { favouritesScrollState.canScrollBackward }
            .distinctUntilChanged()
            .collect { canScroll -> onAction(NewsListAction.OnScrollChanged(!canScroll)) }
    }

    if (favouritesArticles.isEmpty()) {
        SplashText(stringResource(Res.string.empty_favourite_results))
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