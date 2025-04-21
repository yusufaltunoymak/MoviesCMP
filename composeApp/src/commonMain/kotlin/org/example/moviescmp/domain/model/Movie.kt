package org.example.moviescmp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val releaseDate: String,
    val voteAverage: Double = 0.0,
    val language: String = "",
    val popularity: Double = 0.0,
    val genres: List<Genre> = emptyList()
)

data class Genre(
    val id: Int,
    val name: String
)