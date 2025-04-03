package ru.alexmaryin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform