package ru.alexmaryin.news.data.local_api.database

import androidx.room.Embedded
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
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true) val authorId: Int = 0,
    val articleId: Int,
    val name: String,

    @Embedded(prefix = "social_")
    val socials: SocialsEntity
)