package ru.alexmaryin.news.data.dto_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
    val id: Int,
    val title: String,
    val authors: List<AuthorDTO> = emptyList(),
    val url: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("news_site") val newsSite: String,
    val summary: String,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val featured: Boolean,
    val launches: List<LaunchDTO> = emptyList(),
    val events: List<EventDTO> = emptyList(),
)
