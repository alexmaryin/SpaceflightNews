package ru.alexmaryin.news.data.mappers

import ru.alexmaryin.news.data.dto_models.ArticleDTO
import ru.alexmaryin.news.data.dto_models.AuthorDTO
import ru.alexmaryin.news.data.dto_models.EventDTO
import ru.alexmaryin.news.data.dto_models.LaunchDTO
import ru.alexmaryin.news.data.dto_models.SocialsDTO
import ru.alexmaryin.news.domain.models.Article
import ru.alexmaryin.news.domain.models.Author
import ru.alexmaryin.news.domain.models.Event
import ru.alexmaryin.news.domain.models.Launch
import ru.alexmaryin.news.domain.models.Socials

fun ArticleDTO.toArticle() = Article(
    id = id,
    title = title,
    authors = authors.map { it.toAuthor() },
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt,
    updatedAt = updatedAt,
    featured = featured,
    launches = launches.map { it.toLaunch() },
    events = events.map { it.toEvent() },
)

private fun AuthorDTO.toAuthor() = Author(
    name = name,
    socials = socials?.toSocials()
)

private fun SocialsDTO.toSocials() = Socials(
    x = x,
    youTube = youTube,
    instagram = instagram,
    linkedin = linkedin,
    mastodon = mastodon,
    blueSky = blueSky,
)

private fun LaunchDTO.toLaunch() = Launch(
    id = id,
    provider = provider,
)

private fun EventDTO.toEvent() = Event(
    id = id,
    provider = provider,
)