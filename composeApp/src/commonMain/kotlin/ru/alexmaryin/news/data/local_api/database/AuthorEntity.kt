package ru.alexmaryin.news.data.local_api.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val articleId: Int,
    val name: String,
    @Embedded val socials: SocialsEntity? = null
)
