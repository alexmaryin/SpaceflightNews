package ru.alexmaryin.news.data.dto_models

import kotlinx.serialization.Serializable

@Serializable
data class SpaceNewsResponseDTO(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ArticleDTO> = emptyList()
)
