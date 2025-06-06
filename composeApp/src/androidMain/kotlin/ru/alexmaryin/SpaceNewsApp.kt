package ru.alexmaryin

import android.app.Application
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.koin.androidContext
import ru.alexmaryin.di.initKoin

class SpaceNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SpaceNewsApp)
            analytics()
        }
    }
}