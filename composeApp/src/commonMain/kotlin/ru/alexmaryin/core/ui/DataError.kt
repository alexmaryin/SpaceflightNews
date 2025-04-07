package ru.alexmaryin.core.ui

import ru.alexmaryin.core.domain.DataError
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.disk_error
import spaceflightnews.composeapp.generated.resources.no_internet_error
import spaceflightnews.composeapp.generated.resources.request_timeout_error
import spaceflightnews.composeapp.generated.resources.serialization_error
import spaceflightnews.composeapp.generated.resources.server_error
import spaceflightnews.composeapp.generated.resources.too_many_requests_error
import spaceflightnews.composeapp.generated.resources.unknown_error

fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_ERROR -> UiText.StringResourceId(Res.string.disk_error)
        DataError.Local.UNKNOWN_ERROR -> UiText.StringResourceId(Res.string.unknown_error)
        DataError.Remote.REQUEST_TIMEOUT -> UiText.StringResourceId(Res.string.request_timeout_error)
        DataError.Remote.TOO_MANY_REQUESTS -> UiText.StringResourceId(Res.string.too_many_requests_error)
        DataError.Remote.NO_INTERNET_CONNECTION -> UiText.StringResourceId(Res.string.no_internet_error)
        DataError.Remote.SERVER_ERROR -> UiText.StringResourceId(Res.string.server_error)
        DataError.Remote.SERIALIZATION_ERROR -> UiText.StringResourceId(Res.string.serialization_error)
        DataError.Remote.UNKNOWN_ERROR -> UiText.StringResourceId(Res.string.unknown_error)
    }

}