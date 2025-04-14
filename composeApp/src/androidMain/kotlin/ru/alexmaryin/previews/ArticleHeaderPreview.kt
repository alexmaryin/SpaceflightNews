package ru.alexmaryin.previews

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.alexmaryin.news.ui.article_details.components.ArticleHeader

@Preview
@Composable
fun ArticleHeaderPreview() {
    Surface {
        ArticleHeader(
            imageUrl = "",
            title = "Custom title for preview",
            isFavourite = true,
            scrollState = ScrollState(0),
            onBackClick = {},
            onFavouriteClick = {}
        )
    }
}