package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.image_placeholder

@Composable
fun ArticleImage(url: String, title: String, isFavourite: Boolean) {
    Box(
        modifier = Modifier.width(150.dp).padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }
        val painter = rememberAsyncImagePainter(
            model = url,
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
        when (val result = imageLoadResult) {
            null -> CircularProgressIndicator()
            else -> {
                Image(
                    painter = if (result.isSuccess) painter else painterResource(Res.drawable.image_placeholder),
                    contentDescription = title,
                    contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                    modifier = Modifier.aspectRatio(
                        ratio = 0.66f,
                        matchHeightConstraintsFirst = false
                    )
                )
                if (isFavourite) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}