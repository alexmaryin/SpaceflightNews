package ru.alexmaryin.news.data.local_api.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = false) val eventId: Int,
    val articleId: Int,
    val provider: String
)
