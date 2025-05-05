/*
 * Copyright (c) 2025.
 */

package ru.alexmaryin.core.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toDateTimePeriod
import ru.alexmaryin.core.ui.UiText
import spaceflightnews.composeapp.generated.resources.*

fun formatExpiredPeriod(dateTime: String): UiText {
    val published = Instant.parse(dateTime)
    val period = (Clock.System.now() - published).toDateTimePeriod()
    val days = period.hours / 24
    val years = days / 365
    return when {
        years >= 1 -> UiText.PluralResourceId(Res.plurals.years_ago, years, years)
        days > 1 -> UiText.StringResourceId(Res.string.days_ago, days)
        period.hours in 1..24 -> UiText.StringResourceId(Res.string.hours_ago, period.hours)
        period.minutes in 0..60 -> UiText.StringResourceId(Res.string.minutes_ago, period.minutes)
        else -> UiText.DynamicString("")
    }
}