package ru.alexmaryin.di

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.alexmaryin.news.data.local_api.database.ArticlesDbFactory

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<ArticlesDbFactory> { ArticlesDbFactory() }
    }