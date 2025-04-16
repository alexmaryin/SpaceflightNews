package ru.alexmaryin.news.data.local_api.database

import androidx.room.Embedded
import androidx.room.Relation

data class ArticleWithRelations(
    @Embedded val article: ArticleEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "articleId"
    )
    val authors: List<AuthorEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "articleId"
    )
    val launches: List<LaunchEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "articleId"
    )
    val events: List<EventEntity> = emptyList()
)