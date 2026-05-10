package ru.alexmaryin.news.ui.article_details.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import ru.alexmaryin.core.ui.UriHandler
import ru.alexmaryin.core.ui.components.PrimaryContainerText
import ru.alexmaryin.core.ui.components.SurfaceText
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.in_browser
import spaceflightnews.composeapp.generated.resources.read_more
import spaceflightnews.composeapp.generated.resources.summary

@Composable
fun ArticleSummary(
    summary: String,
    url: String
) {
    val uriHandler: UriHandler = koinInject()

    SurfaceText(stringResource(Res.string.summary))

    SurfaceText(summary)

    TitledContent(stringResource(Res.string.read_more)) {
        ArticleChip(
            modifier = Modifier.clickable(enabled = url.isNotEmpty()) {
                uriHandler.openUrl(url)
            }
        ) { PrimaryContainerText(stringResource(Res.string.in_browser)) }
    }
}