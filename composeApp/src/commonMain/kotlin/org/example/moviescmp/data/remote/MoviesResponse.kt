package org.example.moviescmp.data.remote

import kotlinx.serialization.Serializable
import org.example.moviescmp.data.remote.MovieRemote

@Serializable
data class MoviesResponse(
    val results: List<MovieRemote>
)