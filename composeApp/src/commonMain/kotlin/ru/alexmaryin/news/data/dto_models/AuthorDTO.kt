package ru.alexmaryin.news.data.dto_models

import kotlinx.serialization.Serializable

@Serializable
data class AuthorDTO(
    val name: String,
    val socials: SocialsDTO? = null
)
