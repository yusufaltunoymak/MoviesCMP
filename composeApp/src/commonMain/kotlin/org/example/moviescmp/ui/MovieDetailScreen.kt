package org.example.moviescmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import moviescmp.composeapp.generated.resources.Res
import org.example.moviescmp.ui.viewmodel.DetailScreenViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import moviescmp.composeapp.generated.resources.*


@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class, ExperimentalLayoutApi::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    viewModel: DetailScreenViewModel = koinViewModel { parametersOf(movieId) }
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.movie?.title ?: stringResource(Res.string.movie_details)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    Text(
                        text = stringResource(Res.string.error_message, uiState.error ?: ""),
                        color = Color.Red)
                }
                uiState.movie != null -> {
                    val movie = uiState.movie!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            AsyncImage(
                                model = movie.imageUrl,
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color(0x80000000)
                                            )
                                        )
                                    )
                            )

                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = movie.title,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(1f, 1f),
                                            blurRadius = 3f
                                        )
                                    )
                                )

                                SimpleRatingBar(
                                    rating = movie.voteAverage,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(210.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                AsyncImage(
                                    model = movie.imageUrl,
                                    contentDescription = movie.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = stringResource(Res.string.release_date_cd),
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(Res.string.released, movie.releaseDate),
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = stringResource(Res.string.language_cd),
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(Res.string.language, movie.language),
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = stringResource(Res.string.popularity_cd),
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.Red
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(Res.string.popularity, movie.popularity.toInt().toString()),
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = {  },
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = stringResource(Res.string.watch_movie_cd)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(stringResource(Res.string.watch_now))
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                if (movie.genres.isNotEmpty()) {
                                    FlowRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        movie.genres.forEach { genre ->
                                            GenreChip(genre.name)
                                        }
                                    }
                                }

                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Text(
                                text = stringResource(Res.string.overview),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = movie.description,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SimpleRatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    starColor: Color = Color(0xFFFFC107)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = stringResource(Res.string.rating_cd),
            tint = starColor,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = rating.toString().take(3),
            fontSize = 14.sp,
            color = Color.LightGray
        )
    }
}

@Composable
fun GenreChip(genre: String) {
    Surface(
        modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = genre,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}