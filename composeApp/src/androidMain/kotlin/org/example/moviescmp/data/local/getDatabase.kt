package org.example.moviescmp.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getDatabase(context: Context): MovieDatabase {
    val dbFile = context.getDatabasePath("movie.db")
    return Room.databaseBuilder<MovieDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver()).build()
}