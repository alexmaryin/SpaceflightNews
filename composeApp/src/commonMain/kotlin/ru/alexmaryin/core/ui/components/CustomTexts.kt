package ru.alexmaryin.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.by_authors
import spaceflightnews.composeapp.generated.resources.empty_search_results

@Composable
fun SurfaceText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
}

/**
Italic a little transparent text on surface
 **/
@Composable
fun SurfaceIAText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    alpha: Float = 0.33f,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
        modifier = modifier
    )
}

@Composable
fun PrimaryContainerText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun TintedTitle(text: String, tint: Color, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier.fillMaxWidth().padding(16.dp).background(tint)
    )
}

@Composable
fun BodyText (
    text: String,
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
        modifier = modifier.padding(16.dp)
    )
}