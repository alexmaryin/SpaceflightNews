package ru.alexmaryin.news.domain.models

data class Article(
    val id: Int,
    val title: String,
    val authors: List<Author> = emptyList(),
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    val launches: List<Launch> = emptyList(),
    val events: List<Event> = emptyList(),
    val isFavourite: Boolean = false
)
