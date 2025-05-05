package ru.alexmaryin.core.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    data class DynamicString(val value: String): UiText
    class StringResourceId(
        val id: StringResource,
        vararg val args: Any
    ): UiText

    class PluralResourceId(
        val id: PluralStringResource,
        val quantity: Int,
        vararg val args: Any
    ): UiText

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(resource = id, formatArgs = args)
            is PluralResourceId -> pluralStringResource(resource = id, quantity = quantity, formatArgs = args)
        }
    }
}