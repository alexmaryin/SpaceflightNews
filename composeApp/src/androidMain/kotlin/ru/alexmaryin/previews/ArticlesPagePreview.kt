/*
 * Copyright (c) 2025.
 */

package ru.alexmaryin.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.ui.news_list.components.ArticlesPage

private val articles = (1..5).map {
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
private fun ArticlesPreview() {
    val pagedArticles = flowOf(PagingData.from(articles)).collectAsLazyPagingItems()
    MaterialTheme {
        ArticlesPage(
            scrollEvent = null,
            articles = pagedArticles,
            onAction = {}
        )
    }
}