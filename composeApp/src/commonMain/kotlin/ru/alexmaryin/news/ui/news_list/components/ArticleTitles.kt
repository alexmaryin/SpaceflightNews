package ru.alexmaryin.news.ui.news_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.alexmaryin.core.ui.components.SurfaceIAText
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.by_authors

@Composable
fun ArticleTitles(title: String, authors: String?, term: String, summary: String) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            authors?.let {
                SurfaceIAText(stringResource(Res.string.by_authors, it), modifier = Modifier.weight(1f))
            }
            SurfaceIAText(term, modifier = Modifier.weight(1f))
        }
        Text(
            text = summary,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}