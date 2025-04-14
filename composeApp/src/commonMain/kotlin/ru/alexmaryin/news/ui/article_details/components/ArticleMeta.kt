package ru.alexmaryin.news.ui.article_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.serializers.LocalTimeIso8601Serializer
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.PrimaryContainerText
import ru.alexmaryin.core.ui.components.SurfaceText
import ru.alexmaryin.news.domain.models.Author
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.authors
import spaceflightnews.composeapp.generated.resources.from_source
import spaceflightnews.composeapp.generated.resources.published_date
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalLayoutApi::class)
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
        val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val formattedLocal = local.toJavaLocalDateTime().format(
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        )
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
                ArticleChip { PrimaryContainerText(it.name) }
            }
        }
    }
}