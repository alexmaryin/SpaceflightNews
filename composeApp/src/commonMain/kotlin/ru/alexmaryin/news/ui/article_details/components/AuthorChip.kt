package ru.alexmaryin.news.ui.article_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.alexmaryin.core.ui.components.PrimaryContainerText
import ru.alexmaryin.news.domain.models.Author
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.bluesky
import spaceflightnews.composeapp.generated.resources.instagram
import spaceflightnews.composeapp.generated.resources.linkedin
import spaceflightnews.composeapp.generated.resources.mastodon
import spaceflightnews.composeapp.generated.resources.twitter
import spaceflightnews.composeapp.generated.resources.youtube

@Composable
fun AuthorChip(author: Author) {

    val uriHandler = LocalUriHandler.current

    @Composable fun String.placeIcon(icon: DrawableResource) {
        if (isBlank()) return
        IconButton(
            onClick = { uriHandler.openUri(this) }
        ) {
            Icon(
                painterResource(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    }

    Column {
        PrimaryContainerText(author.name)
        author.socials?.let {
            Row {
                it.x.placeIcon(Res.drawable.twitter)
                it.youTube.placeIcon(Res.drawable.youtube)
                it.instagram.placeIcon(Res.drawable.instagram)
                it.linkedin.placeIcon(Res.drawable.linkedin)
                it.blueSky.placeIcon(Res.drawable.bluesky)
                it.mastodon.placeIcon(Res.drawable.mastodon)
            }
        }
    }
}