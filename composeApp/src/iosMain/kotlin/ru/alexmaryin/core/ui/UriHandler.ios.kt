package ru.alexmaryin.core.ui

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class UriHandler {
    actual fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}