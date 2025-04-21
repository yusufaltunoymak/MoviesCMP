package org.example.moviescmp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.example.moviescmp.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("SELECT * FROM MovieEntity")
    suspend fun getMovies(): List<MovieEntity>
}