package org.example.moviescmp.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

fun getDatabase(): MovieDatabase {
    val userHome = System.getProperty("user.home")
    val dbFolder = File(userHome, ".movies_cmp")
    if (!dbFolder.exists()) {
        dbFolder.mkdirs()
    }
    val dbFilePath = File(dbFolder, "movie.db").absolutePath
    println("Database path: $dbFilePath")

    return Room.databaseBuilder<MovieDatabase>(
        name = dbFilePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}