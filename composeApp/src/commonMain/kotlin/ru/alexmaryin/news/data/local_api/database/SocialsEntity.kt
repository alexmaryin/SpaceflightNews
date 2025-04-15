package ru.alexmaryin.news.data.local_api.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SocialsEntity(
    @PrimaryKey(autoGenerate = true)
    @Name
    val id: Long? = null,
    val x: String,
    val youTube: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    val blueSky: String,
)
