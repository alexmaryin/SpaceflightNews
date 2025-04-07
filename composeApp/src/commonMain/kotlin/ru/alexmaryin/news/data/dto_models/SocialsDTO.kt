package ru.alexmaryin.news.data.dto_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialsDTO(
    val x: String,
    val youTube: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    @SerialName("bluesky") val blueSky: String,
)
