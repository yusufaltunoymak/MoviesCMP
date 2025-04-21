package org.example.moviescmp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import moviescmp.composeapp.generated.resources.*
import moviescmp.composeapp.generated.resources.Res
import org.example.moviescmp.ui.viewmodel.HomeScreenUiState
import org.example.moviescmp.ui.viewmodel.HomeScreenViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = koinViewModel()
) {
    val uiState: HomeScreenUiState by homeScreenViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(Res.string.home)) }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = { homeScreenViewModel.refresh() }
            ) {
                when {
                    uiState.isLoading && !uiState.isRefreshing -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    uiState.error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(
                                    Res.string.error_message,
                                    uiState.error ?: ""
                                ),
                                color = Color.Red
                            )
                        }
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(uiState.movies) { movie ->
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { onMovieClick(movie.id) }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(8.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(2f / 3f)
                                                .clip(RoundedCornerShape(8.dp))
                                            ,
                                            model = movie.imageUrl,
                                            contentDescription = null
                                        )
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(4.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth().padding(4.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = movie.title,
                                                    modifier = Modifier.weight(1f)
                                                        .padding(end = 8.dp),
                                                    fontSize = 12.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Text(text = movie.releaseDate, fontSize = 12.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
    }
}