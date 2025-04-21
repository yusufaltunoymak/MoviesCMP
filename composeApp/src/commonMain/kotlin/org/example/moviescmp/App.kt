package org.example.moviescmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.moviescmp.ui.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigation()
    }
}