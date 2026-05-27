package ru.alexmaryin.di

import coil3.ImageLoader
import coil3.PlatformContext

expect fun createImageLoader(context: PlatformContext): ImageLoader
