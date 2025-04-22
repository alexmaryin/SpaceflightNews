package ru.alexmaryin.news.ui.article_details.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.FavouriteGradient
import ru.alexmaryin.core.ui.components.TintedTitle
import ru.alexmaryin.core.ui.components.VerticalGradient
import ru.alexmaryin.news.ui.article_details.ImageAnimation
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.back_button
import spaceflightnews.composeapp.generated.resources.favourite_button

@Composable
fun ArticleHeader(
    imageUrl: String,
    title: String,
    isFavourite: Boolean,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    val scrollRangePx = with(LocalDensity.current) { ImageAnimation.scrollRange.toPx() }
    val scrollOffset = scrollState.value.toFloat().coerceIn(0f, scrollRangePx)
    val scrollFraction = (scrollOffset / scrollRangePx).coerceIn(0f, 1f)

    val imageHeight by animateDpAsState(
        targetValue = lerp(ImageAnimation.maxHeight, ImageAnimation.minHeight, scrollFraction),
        label = "Image Height Animation"
    )

    val blurRadius by animateDpAsState(
        targetValue = lerp(0.dp, ImageAnimation.maxBlur, scrollFraction),
        label = "Blur Animation"
    )
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
            model = imageUrl,
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
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(imageHeight).blur(
                    radius = blurRadius,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
            )
        }

        // Main Title
        TintedTitle(
            text = title,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.66f),
            modifier = Modifier.align(Alignment.BottomStart)
        )

        // Back button
        IconButton(
            onClick = onBackClick,
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

        // Favourite button
        IconButton(
            onClick = onFavouriteClick,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.TopEnd)
                .background(brush = FavouriteGradient(isFavourite))
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(Res.string.favourite_button),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}