package ru.alexmaryin.di

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.CachePolicy

actual fun createImageLoader(context: PlatformContext): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()
}
