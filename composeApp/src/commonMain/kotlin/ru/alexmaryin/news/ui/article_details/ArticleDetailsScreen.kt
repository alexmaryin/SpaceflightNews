package ru.alexmaryin.news.ui.article_details

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.PrimaryContainerText
import ru.alexmaryin.core.ui.components.SurfaceText
import ru.alexmaryin.core.ui.components.TintedTitle
import ru.alexmaryin.core.ui.components.VerticalGradient
import ru.alexmaryin.news.ui.article_details.components.ArticleChip
import ru.alexmaryin.news.ui.article_details.components.TitledContent
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.authors
import spaceflightnews.composeapp.generated.resources.back_button
import spaceflightnews.composeapp.generated.resources.from_source
import spaceflightnews.composeapp.generated.resources.in_browser
import spaceflightnews.composeapp.generated.resources.published_date
import spaceflightnews.composeapp.generated.resources.read_more
import spaceflightnews.composeapp.generated.resources.summary

@Composable
fun ArticlesDetailsScreenRoot(

) {

}

object ImageAnimation {
    val minHeight = 50.dp   // height of collapsed article image
    val maxHeight = 400.dp  // initial height of article image
    val maxBlur = 20.dp     // blur radius for collapsed image
    val scrollRange = 300.dp // scroll sensitivity for collapsing
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticlesDetailsScreen(
    state: ArticleDetailsState,
    onAction: (ArticleDetailsAction) -> Unit
) {
    val scrollState = rememberScrollState()
    val scrollRangePx = with(LocalDensity.current) { ImageAnimation.scrollRange.toPx() }
    val scrollOffset = scrollState.value.toFloat().coerceIn(0f, scrollRangePx)
    val scrollFraction = (scrollOffset / scrollRangePx).coerceIn(0f, 1f)
    val uriHandler = LocalUriHandler.current

    val imageHeight by animateDpAsState(
        targetValue = lerp(ImageAnimation.maxHeight, ImageAnimation.minHeight, scrollFraction),
        label = "Image Height Animation"
    )

    val blurRadius by animateDpAsState(
        targetValue = lerp(0.dp, ImageAnimation.maxBlur, scrollFraction),
        label = "Blur Animation"
    )

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright)
            .statusBarsPadding()
//            .padding(12.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // title image
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(VerticalGradient()),
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
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(imageHeight).blur(
                        radius = blurRadius,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
                )
            }

            // Main Title
            TintedTitle(
                text = state.article!!.title,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.66f),
                modifier = Modifier.align(Alignment.BottomStart)
            )

            // Back button
            IconButton(
                onClick = { onAction(ArticleDetailsAction.onBackClick) },
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(100)
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(Res.string.back_button),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

        }

        state.article?.let { article ->

            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TitledContent(title = stringResource(Res.string.from_source)) {
                    ArticleChip { PrimaryContainerText(article.newsSite) }
                }
                TitledContent(title = stringResource(Res.string.published_date)) {
                    ArticleChip { PrimaryContainerText(article.publishedAt) }
                }
            }

            if (article.authors.isNotEmpty()) {
                SurfaceText(pluralStringResource(Res.plurals.authors, article.authors.size))
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    article.authors.map {
                        ArticleChip { PrimaryContainerText(it.name) }
                    }
                }
            }

            SurfaceText(stringResource(Res.string.summary))

            SurfaceText(article.summary)

            TitledContent(stringResource(Res.string.read_more)) {
                ArticleChip(
                    modifier = Modifier.clickable(enabled = article.url.isNotEmpty()) {
                        uriHandler.openUri(article.url)
                    }
                ) { PrimaryContainerText(stringResource(Res.string.in_browser)) }
            }

            Spacer(Modifier.height(50.dp))
        }
    }
}