package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock.System
import kotlinx.datetime.Instant
import kotlinx.datetime.toDateTimePeriod
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.isCompact
import ru.alexmaryin.core.ui.rememberScreenSizeInfo
import ru.alexmaryin.news.domain.models.Article
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.days_ago
import spaceflightnews.composeapp.generated.resources.hours_ago
import spaceflightnews.composeapp.generated.resources.minutes_ago
import spaceflightnews.composeapp.generated.resources.open_details

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val windowSize = rememberScreenSizeInfo()

    OutlinedCard(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.outlinedCardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        val adaptedContent = @Composable {
            val authors = if (article.authors.isNotEmpty()) article.authors.joinToString { it.name } else null
            val published = Instant.parse(article.publishedAt)
            val period = (System.now() - published).toDateTimePeriod()
//            println("${period.days} ${period.hours} ${period.minutes}") // TODO extract to function in core utils
            val term = when {
                period.hours > 24 -> stringResource(Res.string.days_ago, period.hours / 24)
                period.hours in 1..24 -> stringResource(Res.string.hours_ago, period.hours)
                period.minutes in 0..60 -> stringResource(Res.string.minutes_ago, period.minutes)
                else -> ""
            }
            ArticleTitles(article.title, authors, term, article.summary)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (windowSize.isCompact()) {
                Column(
                    modifier = Modifier.padding(16.dp).weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ArticleImage(isCompact = true, article.imageUrl, article.title)
                    adaptedContent()
                }
            } else {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min).padding(16.dp).weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ArticleImage(isCompact = false, article.imageUrl, article.title)
                    adaptedContent()
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(Res.string.open_details),
                modifier = Modifier.size(40.dp)
            )
        }


    }
}
