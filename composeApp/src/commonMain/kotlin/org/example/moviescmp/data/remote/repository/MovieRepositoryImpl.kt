package org.example.moviescmp.data.remote.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.moviescmp.data.local.MovieDatabase
import org.example.moviescmp.data.local.entity.MovieEntity
import org.example.moviescmp.data.remote.RemoteDataSource
import org.example.moviescmp.data.remote.Video
import org.example.moviescmp.domain.model.Movie
import org.example.moviescmp.domain.repository.MovieRepository
import org.example.moviescmp.util.Resource
import org.example.moviescmp.util.toMovie

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val movieDatabase: MovieDatabase
): MovieRepository {

    override fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading())
            try {
                val movie = remoteDataSource.getMovie(id).toMovie()
                emit(Resource.Success(movie))
            }
            catch(e: Exception) {
                emit(Resource.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }

    override fun getMovieVideos(movieId: Int): Flow<Resource<List<Video>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val videos = remoteDataSource.getMovieVideos(movieId).results
                emit(Resource.Success(videos))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred while fetching videos"))
            }
        }
    }


    override fun getMovies(page: Int, forceFetch: Boolean): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            if (!forceFetch) {
                try {
                    val localMovies = movieDatabase.movieDao().getMovies()
                    if (localMovies.isNotEmpty()) {
                        println("localMovies found: ${localMovies.size}")
                        emit(Resource.Success(localMovies.map { it.toMovie() }))
                        return@flow
                    }
                } catch (e: Exception) {
                    // TODO
                }
            }

            try {
                val remoteMovies = remoteDataSource.getMovies(page).results.map {
                    it.toMovie()
                }
                emit(Resource.Success(remoteMovies))
                println("remoteMovies found: ${remoteMovies.size}")

                movieDatabase.movieDao().upsertMovieList(
                    remoteMovies.map { movie ->
                        MovieEntity(
                            id = movie.id,
                            title = movie.title,
                            overview = movie.description,
                            posterImage = movie.imageUrl.removePrefix("https://image.tmdb.org/t/p/w500"),
                            releaseDate = movie.releaseDate
                        )
                    }
                )
            } catch(e: Exception) {
                emit(Resource.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }

}