package ru.alexmaryin.news.data.mappers

import ru.alexmaryin.news.data.local_api.database.ArticleEntity
import ru.alexmaryin.news.data.local_api.database.ArticleWithRelations
import ru.alexmaryin.news.data.local_api.database.AuthorEntity
import ru.alexmaryin.news.data.local_api.database.EventEntity
import ru.alexmaryin.news.data.local_api.database.LaunchEntity
import ru.alexmaryin.news.data.local_api.database.SocialsEntity
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.domain.models.Event
import ru.alexmaryin.news.domain.models.Launch
import ru.alexmaryin.news.domain.models.Socials

private fun Socials.toEntity() = SocialsEntity(
    x = x ?: "",
    youTube = youTube ?: "",
    instagram = instagram ?: "",
    linkedin = linkedin ?: "",
    mastodon = mastodon ?: "",
    blueSky = blueSky ?: ""
)

private fun Launch.toEntity(articleId: Int) = LaunchEntity(
    id = id,
    articleId = articleId,
    provider = provider
)

private fun Event.toEntity(articleId: Int) = EventEntity(
    articleId = articleId,
    provider = provider
)

private fun Author.toEntity(articleId: Int) = AuthorEntity(
    articleId = articleId,
    name = name,
    socials = socials.toEntity()
)

fun Article.toEntity() = ArticleWithRelations(
    article = ArticleEntity(
        id = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        featured = featured
    ),
    authors = authors.map { it.toEntity(id) },
    launches = launches.map { it.toEntity(id) },
    events = events.map { it.toEntity(id) },
)