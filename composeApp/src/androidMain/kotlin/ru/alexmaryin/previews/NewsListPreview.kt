package ru.alexmaryin.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.alexmaryin.core.ui.UiText
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.ui.news_list.components.ArticlesList
import ru.alexmaryin.news.ui.news_list.NewsListScreen
import ru.alexmaryin.news.ui.news_list.NewsListScreenRoot
import ru.alexmaryin.news.ui.news_list.NewsListState
import kotlin.random.Random

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
        isFavourite = Random.nextBoolean()
    )
}

@Preview(showBackground = true)
@Composable
private fun NewsListPreview() {
    MaterialTheme {
        NewsListScreen(
            NewsListState(
                searchQuery = "",
                searchResult = articles, // emptyList(),
                favouriteArticles = emptyList(),
                isLoading = false,
                selectedTabIndex = 0,
                error = null // UiText.DynamicString("Unknown error!")
            ),
        ) {}
    }
}