package ru.alexmaryin.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.ui.news_list.NewsListScreen
import ru.alexmaryin.news.ui.news_list.NewsListState

private val articles = (1..100).map {
    Article(
        id = it,
        title = "Article $it",
        authors = listOf(Author(name = "Alex Maryin")),
        url = "",
        imageUrl = "",
        newsSite = "",
        summary = "This is the article with number No. $it written by me.",
        publishedAt = "",
        updatedAt = "",
        featured = false,
    )
}

@Preview(showBackground = true)
@Composable
private fun NewsListPreview() {
    MaterialTheme {
        NewsListScreen(
            NewsListState(
                searchQuery = "",
                articlesFlow = flowOf(PagingData.from(articles)),
                favouriteArticles = emptyList(),
                selectedTabIndex = 1,
                error = null // UiText.DynamicString("Unknown error!")
            ),
            onAction = {},
            onSideMenuAction = {}
        )
    }
}