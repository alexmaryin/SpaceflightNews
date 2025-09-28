package ru.alexmaryin.news.util

import kotlin.time.ExperimentalTime
import kotlin.time.Instant


/**
 * Formats an [Instant] to a date string using the system's locale.
 */
@OptIn(ExperimentalTime::class)
expect fun Instant.formatToSystemLocaleDate(): String