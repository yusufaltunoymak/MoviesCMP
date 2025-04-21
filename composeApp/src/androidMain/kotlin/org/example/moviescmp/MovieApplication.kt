package org.example.moviescmp

import android.app.Application
import org.example.moviescmp.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@MovieApplication)
        }
    }
}