package ru.alexmaryin.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.alexmaryin.news.data.local_api.database.ArticlesDbFactory

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { CIO.create() }
        single<ArticlesDbFactory> { ArticlesDbFactory(androidApplication()) }
    }