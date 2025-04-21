package org.example.moviescmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform