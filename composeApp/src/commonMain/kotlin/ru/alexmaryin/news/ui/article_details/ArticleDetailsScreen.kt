package ru.alexmaryin.news.ui.article_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.alexmaryin.news.ui.article_details.components.ArticleHeader
import ru.alexmaryin.news.ui.article_details.components.ArticleMeta
import ru.alexmaryin.news.ui.article_details.components.ArticleSummary

@Composable
fun ArticlesDetailsScreenRoot(
    viewModel: ArticleDetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ArticlesDetailsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ArticleDetailsAction.onBackClick -> onBackClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

object ImageAnimation {
    val minHeight = 50.dp   // height of collapsed article image
    val maxHeight = 400.dp  // initial height of article image
    val maxBlur = 20.dp     // blur radius for collapsed image
    val scrollRange = 300.dp // scroll sensitivity for collapsing
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ArticlesDetailsScreen(
    state: ArticleDetailsState,
    onAction: (ArticleDetailsAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright)
            .systemBarsPadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.article?.let { article ->
            ArticleHeader(
                imageUrl = article.imageUrl,
                title = article.title,
                isFavourite = article.isFavourite,
                scrollState = scrollState,
                onBackClick = { onAction(ArticleDetailsAction.onBackClick) },
                onFavouriteClick = { onAction(ArticleDetailsAction.onFavouriteClick) }
            )

            ArticleMeta(
                source = article.newsSite,
                publicationDate = article.publishedAt,
                authors = article.authors
            )

            ArticleSummary(
                summary = article.summary,
                url = article.url
            )

            Spacer(Modifier.height(50.dp))
        }
    }
}