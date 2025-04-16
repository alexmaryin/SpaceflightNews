package ru.alexmaryin.news.data.local_api.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = ArticleEntity::class,
        parentColumns = ["id"],
        childColumns = ["articleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("articleId")]
)
data class LaunchEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val articleId: Int,
    val provider: String
)