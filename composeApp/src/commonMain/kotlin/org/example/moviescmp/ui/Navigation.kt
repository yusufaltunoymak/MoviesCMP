package org.example.moviescmp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.moviescmp.ui.HomeScreen
import org.example.moviescmp.ui.MovieDetailScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen"){
        composable("home_screen") {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate("detail/$movieId")
                }
            )
        }

        composable(
            route = "detail/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )

        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}