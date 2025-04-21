package org.example.moviescmp.di

import org.example.moviescmp.data.local.MovieDatabase
import org.example.moviescmp.data.local.getDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<MovieDatabase> { getDatabase(get()) }
}