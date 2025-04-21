package org.example.moviescmp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.moviescmp.domain.model.Movie
import org.example.moviescmp.domain.repository.MovieRepository
import org.example.moviescmp.util.Resource

class HomeScreenViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchMovies(false)
    }

    fun refresh() {
        fetchMovies(true)
    }

    private fun fetchMovies(forceFetch: Boolean) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isRefreshing = forceFetch) }

                repository.getMovies(1, forceFetch).collectLatest { result ->
                    when(result) {
                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    movies = result.data ?: emptyList(),
                                    error = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    error = result.message
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = !forceFetch) }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }
}



data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null
)