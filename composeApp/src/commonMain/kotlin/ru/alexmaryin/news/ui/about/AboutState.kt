package ru.alexmaryin.news.ui.about

data class AboutState(
    val url: String = URL,
    val contactInfo: String = EMAIL,
    val privacyPolicyUrl: String = PRIVACY_POLICY,
) {
    companion object {
        const val URL = "https://sites.google.com/view/spacenews-explorer"
        const val EMAIL = "java.ul@gmail.com"
        const val PRIVACY_POLICY = "https://sites.google.com/view/spacenews-explorer/privacy-policy"
    }
}
