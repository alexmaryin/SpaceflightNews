package ru.alexmaryin.di

import coil3.PlatformContext
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.alexmaryin.core.ui.UriHandler
import ru.alexmaryin.news.data.local_api.database.ArticlesDbFactory

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single<PlatformContext> { PlatformContext.INSTANCE }
        single<ArticlesDbFactory> { ArticlesDbFactory() }
        single { UriHandler() }
    }