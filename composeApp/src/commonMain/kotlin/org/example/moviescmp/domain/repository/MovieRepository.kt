package org.example.moviescmp.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.moviescmp.util.Resource
import org.example.moviescmp.data.remote.Video
import org.example.moviescmp.domain.model.Movie

interface MovieRepository {
    fun getMovies(page: Int, forceFetch: Boolean): Flow<Resource<List<Movie>>>
    fun getMovie(id: Int): Flow<Resource<Movie>>
    fun getMovieVideos(movieId: Int): Flow<Resource<List<Video>>>
}