package ru.alexmaryin.news.ui.article_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.PrimaryContainerText
import ru.alexmaryin.core.ui.components.SurfaceText
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.util.formatToSystemLocaleDate
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.authors
import spaceflightnews.composeapp.generated.resources.from_source
import spaceflightnews.composeapp.generated.resources.published_date
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalLayoutApi::class, ExperimentalTime::class)
@Composable
fun ArticleMeta(
    source: String,
    publicationDate: String,
    authors: List<Author>
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TitledContent(title = stringResource(Res.string.from_source)) {
            ArticleChip { PrimaryContainerText(source) }
        }
        val instant = Instant.parse(publicationDate)
        val formattedLocal = instant.formatToSystemLocaleDate()
        TitledContent(title = stringResource(Res.string.published_date)) {
            ArticleChip { PrimaryContainerText(formattedLocal) }
        }
    }

    if (authors.isNotEmpty()) {
        SurfaceText(pluralStringResource(Res.plurals.authors, authors.size))
        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            authors.map {
                ArticleChip {
                    AuthorChip(it)
                }
            }
        }
    }
}