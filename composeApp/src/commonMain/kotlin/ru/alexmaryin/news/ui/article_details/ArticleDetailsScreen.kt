package ru.alexmaryin.news.ui.article_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.alexmaryin.news.ui.article_details.components.ArticleChip
import ru.alexmaryin.news.ui.article_details.components.TitledContent

@Composable
fun ArticlesDetailsScreenRoot(

) {

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticlesDetailsScreen(
    state: ArticleDetailsState,
    onAction: (ArticleDetailsAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title image
        Box(
            modifier = Modifier.height(400.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            var imageLoadResult by remember {
                mutableStateOf<Result<Painter>?>(null)
            }
            val painter = rememberAsyncImagePainter(
                model = state.article?.imageUrl,
                onSuccess = {
                    imageLoadResult =
                        if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(Exception("Invalid image size"))
                        }
                },
                onError = {
                    it.result.throwable.printStackTrace()
                    imageLoadResult = Result.failure(it.result.throwable)
                }
            )
            imageLoadResult?.getOrNull()?.let {
                Image(
                    painter = painter,
                    contentDescription = state.article?.title,
                    contentScale = ContentScale.Crop
                )
            }
        }

        // title
        state.article?.let { article ->
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TitledContent(title = "from source:") {
                    ArticleChip {
                        Text(text = article.newsSite)
                    }
                }
                TitledContent(title = "published:") {
                    ArticleChip {
                        Text(text = article.publishedAt)
                    }
                }
            }

            if (article.authors.isNotEmpty()) {
                Text("Authors:")
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    article.authors.map {
                        ArticleChip {
                            Text(text = it.name)
                        }
                    }
                }
            }

            Text("Summary:")

            Text(article.summary)
        }
    }
}