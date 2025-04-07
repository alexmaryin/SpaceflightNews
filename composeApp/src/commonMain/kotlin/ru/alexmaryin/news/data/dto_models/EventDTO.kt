package ru.alexmaryin.news.data.dto_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDTO(
    @SerialName("event_id") val id: Int,
    val provider: String

)
