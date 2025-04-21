package org.example.moviescmp.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.example.moviescmp.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
    override fun initialize(): MovieDatabase
}
