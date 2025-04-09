package ru.alexmaryin.news.ui.article_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

enum class ChipSize {
    SMALL, REGULAR, LARGE
}

@Composable
fun ArticleChip(
    modifier: Modifier = Modifier,
    size: ChipSize = ChipSize.REGULAR,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.widthIn(
            min = when (size) {
                ChipSize.SMALL -> 50.dp
                ChipSize.REGULAR -> 80.dp
                ChipSize.LARGE -> 120.dp
            }
        )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
        ) {
        content()
    }
}