package ru.alexmaryin.core.domain

sealed interface DataError : Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET_CONNECTION,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN_ERROR
    }

    enum class Local: DataError {
        DISK_ERROR,
        UNKNOWN_ERROR
    }
}