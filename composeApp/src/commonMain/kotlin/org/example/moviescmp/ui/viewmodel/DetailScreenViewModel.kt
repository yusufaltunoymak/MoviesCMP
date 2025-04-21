package org.example.moviescmp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.moviescmp.util.Resource
import org.example.moviescmp.data.remote.Video
import org.example.moviescmp.domain.model.Movie
import org.example.moviescmp.domain.repository.MovieRepository

class DetailScreenViewModel(
    private val repository: MovieRepository,
    private val movieId: Int
): ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchMovieDetails()
        fetchMovieVideos()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            try {
                repository.getMovie(movieId).collectLatest { result ->
                    when(result) {
                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    movie = result.data,
                                    error = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    private fun fetchMovieVideos() {
        viewModelScope.launch {
            try {
                repository.getMovieVideos(movieId).collectLatest { result ->
                    when(result) {
                        is Resource.Success -> {
                            val trailers = result.data?.filter {
                                it.site.equals("YouTube", ignoreCase = true) &&
                                        it.type.equals("Trailer", ignoreCase = true)
                            }
                            _uiState.update {
                                it.copy(
                                    videos = trailers!!,
                                    selectedVideo = trailers.firstOrNull()
                                )
                            }
                        }
                        is Resource.Error -> {
                            println("Error loading videos: ${result.message}")
                        }
                        is Resource.Loading -> {
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error fetching videos: ${e.message}")
            }
        }
    }

    fun selectVideo(video: Video) {
        _uiState.update { it.copy(selectedVideo = video) }
    }
}

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String? = null,
    val videos: List<Video> = emptyList(),
    val selectedVideo: Video? = null
)