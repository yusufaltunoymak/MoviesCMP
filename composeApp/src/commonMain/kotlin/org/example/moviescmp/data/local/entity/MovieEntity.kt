package org.example.moviescmp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterImage: String,
    val releaseDate: String
)