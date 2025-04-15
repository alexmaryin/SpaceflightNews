package ru.alexmaryin.news.data.local_api.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LaunchEntity(
    @PrimaryKey(autoGenerate = false) val launchId: String,
    val articleId: Int,
    val provider: String
)
