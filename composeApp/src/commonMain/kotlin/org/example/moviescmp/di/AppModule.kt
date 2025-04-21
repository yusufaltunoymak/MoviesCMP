package org.example.moviescmp.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.moviescmp.data.remote.RemoteDataSource
import org.example.moviescmp.data.remote.repository.MovieRepositoryImpl
import org.example.moviescmp.domain.repository.MovieRepository
import org.example.moviescmp.ui.viewmodel.DetailScreenViewModel
import org.example.moviescmp.ui.viewmodel.HomeScreenViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    // Ktor HttpClient configuration
    single {
        HttpClient() {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    // RemoteDataSource
    single { RemoteDataSource(get()) }

    // Repository
    single<MovieRepository> { MovieRepositoryImpl(get(),get()) }

    // ViewModel
    viewModel { HomeScreenViewModel(get()) }
    viewModel { parameters -> DetailScreenViewModel(get(), parameters.get()) }
}

expect fun platformModule() : Module


fun initializeKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(appModule, platformModule())
    }