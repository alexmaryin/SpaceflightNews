package ru.alexmaryin.news.data.dto_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDTO(
    @SerialName("launch_id") val id: String, // UUID type
    val provider: String
)