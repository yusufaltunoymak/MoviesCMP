package org.example.moviescmp.util

import org.example.moviescmp.data.local.entity.MovieEntity
import org.example.moviescmp.data.remote.MovieRemote
import org.example.moviescmp.domain.model.Genre
import org.example.moviescmp.domain.model.Movie

fun MovieRemote.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        imageUrl = "https://image.tmdb.org/t/p/w500$posterImage",
        releaseDate = releaseDateToYear(releaseDate),
        voteAverage = voteAverage,
        language = language,
        popularity = popularity,
        genres = genres.map { Genre(it.id, it.name) }
    )
}

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    description = overview,
    imageUrl = getImageUrl(posterImage),
    releaseDate = releaseDateToYear(releaseDate),
    voteAverage = 0.0,
    language = "",
    popularity = 0.0,
    genres = emptyList()
)

private fun getImageUrl(posterImage: String) = "https://image.tmdb.org/t/p/w500$posterImage"

private fun releaseDateToYear(releaseDate: String): String {
    return releaseDate.split("-").first()
}