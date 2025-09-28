package ru.alexmaryin.core

import java.io.File

fun getAppDataDir(): File {
    val os = System.getProperty("os.name")?.lowercase() ?: ""
    val userHome = System.getProperty("user.home")
    return when {
        os.contains("win") -> File(System.getenv("APPDATA"), "Space news")
        os.contains("mac") -> File(userHome, "Library/Application Support/Space news")
        else -> File(userHome, ".local/share/Space news")
    }
}