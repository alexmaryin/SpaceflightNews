package ru.alexmaryin.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.ui.article_details.ArticleDetailsState
import ru.alexmaryin.news.ui.article_details.ArticlesDetailsScreen

@Preview
@Composable
private fun ArticleDetailsPreview() {
    MaterialTheme {
        ArticlesDetailsScreen(
            ArticleDetailsState(article = Article(
                id = 1,
                title = "Article title big",
                authors = listOf(Author(name = "Alex Maryin")),
                url = "",
                imageUrl = "",
                newsSite = "Fiction",
                summary = "Summary of the article\n".repeat(100),
                publishedAt = "01.01.2025",
                updatedAt = "01.01.2025",
                featured = false,
                launches = emptyList(),
                events = emptyList(),
                isFavourite = false
            ))
        ) { }
    }
}