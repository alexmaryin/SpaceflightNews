package ru.alexmaryin.previews

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil3.SingletonImageLoader
import coil3.compose.LocalPlatformContext
import ru.alexmaryin.news.ui.article_details.components.ArticleHeader

@Preview
@Composable
fun ArticleHeaderPreview() {
    Surface {
        ArticleHeader(
            imageUrl = "https://spaceflightnow.com/wp-content/uploads/2026/05/20260508-Testing-Link-Vibration-tests-2.jpg",
            title = "Custom title for preview",
            isFavourite = true,
            scrollState = ScrollState(0),
            onBackClick = {},
            onFavouriteClick = {},
            imageLoader = SingletonImageLoader.get(LocalPlatformContext.current)
        )
    }
}