package ru.alexmaryin.news.data.util

fun String.ensureHttps(): String = replace("http://", "https://")