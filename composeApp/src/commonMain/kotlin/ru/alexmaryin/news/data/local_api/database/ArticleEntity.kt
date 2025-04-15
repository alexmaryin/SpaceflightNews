package ru.alexmaryin.news.data.local_api.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    @Relation(parentColumn = "id", entityColumn = "articleId")
    val authors: List<AuthorEntity> = emptyList(),
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    @Relation(parentColumn = "id", entityColumn = "articleId")
    val launches: List<LaunchEntity> = emptyList(),
    @Relation(parentColumn = "id", entityColumn = "articleId")
    val events: List<EventEntity> = emptyList(),
)
