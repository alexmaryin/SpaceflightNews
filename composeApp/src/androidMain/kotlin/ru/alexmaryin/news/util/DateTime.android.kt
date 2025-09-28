package ru.alexmaryin.news.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJavaInstant

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalTime::class)
actual fun Instant.formatToSystemLocaleDate(): String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withZone(ZoneId.systemDefault())
    return formatter.format(this.toJavaInstant())
}