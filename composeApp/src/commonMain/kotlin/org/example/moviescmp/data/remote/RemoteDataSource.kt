package org.example.moviescmp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.example.moviescmp.data.remote.MovieRemote
import org.example.moviescmp.data.remote.MoviesResponse
import org.example.moviescmp.data.remote.VideoResponse

class RemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getMovies(page: Int): MoviesResponse = withContext(Dispatchers.IO) {
        val response = httpClient.get("https://api.themoviedb.org/3/movie/popular") {
            parameter("api_key", "xxxxx")
            parameter("page", page)
        }
        return@withContext response.body()
    }

    suspend fun getMovie(movieId: Int): MovieRemote = withContext(Dispatchers.IO) {
        val response = httpClient.get("https://api.themoviedb.org/3/movie/$movieId") {
            parameter("api_key", "xxxxxx")
        }
        return@withContext response.body()
    }

    suspend fun getMovieVideos(movieId: Int): VideoResponse = withContext(Dispatchers.IO) {
        val response = httpClient.get("https://api.themoviedb.org/3/movie/$movieId/videos") {
            parameter("api_key", "xxxxxx")
        }
        return@withContext response.body()
    }
}