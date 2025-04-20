package ru.alexmaryin.news.data.mappers

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

fun ArticleWithRelations.toArticle() = Article(
    id = article.id,
    title = article.title,
    authors = authors.map { it.toAuthor() },
    url = article.url,
    imageUrl = article.imageUrl,
    newsSite = article.newsSite,
    summary = article.summary,
    publishedAt = article.publishedAt,
    updatedAt = article.updatedAt,
    featured = article.featured,
    launches = launches.map { it.toLaunch() },
    events = events.map { it.toEvent() },
    isFavourite = article.isFavourite
)

fun EventEntity.toEvent() = Event(
    id = id,
    provider = provider
)

fun LaunchEntity.toLaunch() = Launch(
    id = id,
    provider = provider
)

fun SocialsEntity.toSocials() = Socials(
    x = x,
    youTube = youTube,
    instagram = instagram,
    linkedin = linkedin,
    mastodon = mastodon,
    blueSky = blueSky
)

fun AuthorEntity.toAuthor() = Author(
    name = name,
    socials = socials?.toSocials()
)