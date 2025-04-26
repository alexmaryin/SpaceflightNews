package ru.alexmaryin.core.ui

import ru.alexmaryin.core.domain.DataError
import ru.alexmaryin.core.ui.UiText.*
import spaceflightnews.composeapp.generated.resources.Res
import spaceflightnews.composeapp.generated.resources.bad_request_error
import spaceflightnews.composeapp.generated.resources.conflict_error
import spaceflightnews.composeapp.generated.resources.disk_error
import spaceflightnews.composeapp.generated.resources.forbidden_error
import spaceflightnews.composeapp.generated.resources.no_internet_error
import spaceflightnews.composeapp.generated.resources.not_found_error
import spaceflightnews.composeapp.generated.resources.request_timeout_error
import spaceflightnews.composeapp.generated.resources.serialization_error
import spaceflightnews.composeapp.generated.resources.server_error
import spaceflightnews.composeapp.generated.resources.too_many_requests_error
import spaceflightnews.composeapp.generated.resources.unauthorized_error
import spaceflightnews.composeapp.generated.resources.unknown_error

fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_ERROR -> StringResourceId(Res.string.disk_error)
        DataError.Local.UNKNOWN_ERROR -> StringResourceId(Res.string.unknown_error)
        DataError.Remote.REQUEST_TIMEOUT -> StringResourceId(Res.string.request_timeout_error)
        DataError.Remote.TOO_MANY_REQUESTS -> StringResourceId(Res.string.too_many_requests_error)
        DataError.Remote.NO_INTERNET_CONNECTION -> StringResourceId(Res.string.no_internet_error)
        DataError.Remote.SERVER_ERROR -> StringResourceId(Res.string.server_error)
        DataError.Remote.SERIALIZATION_ERROR -> StringResourceId(Res.string.serialization_error)
        DataError.Remote.UNKNOWN_ERROR -> StringResourceId(Res.string.unknown_error)
        DataError.Remote.BAD_REQUEST -> StringResourceId(Res.string.bad_request_error)
        DataError.Remote.UNAUTHORIZED -> StringResourceId(Res.string.unauthorized_error)
        DataError.Remote.FORBIDDEN -> StringResourceId(Res.string.forbidden_error)
        DataError.Remote.NOT_FOUND -> StringResourceId(Res.string.not_found_error)
        DataError.Remote.CONFLICT -> StringResourceId(Res.string.conflict_error)
    }
}