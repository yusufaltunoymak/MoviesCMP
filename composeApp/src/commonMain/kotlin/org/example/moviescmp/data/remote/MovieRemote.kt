package org.example.moviescmp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieRemote(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterImage: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("original_language")
    val language: String = "",
    val popularity: Double = 0.0,
    @SerialName("genres")
    val genres: List<GenreRemote> = emptyList()
)

@Serializable
data class GenreRemote(
    val id: Int,
    val name: String
)


@Serializable
data class VideoResponse(
    val results: List<Video> = emptyList()
)

@Serializable
data class Video(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean = false,
    @SerialName("published_at")
    val publishedAt: String = ""
)