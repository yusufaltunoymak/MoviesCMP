package org.example.moviescmp

import androidx.compose.ui.window.ComposeUIViewController
import org.example.moviescmp.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeKoin()
    }
) { App() }