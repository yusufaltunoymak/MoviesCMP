package org.example.moviescmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.moviescmp.di.initializeKoin

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "MoviesCMP",
    ) {
        App()
    }
}