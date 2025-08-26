package com.example.counterapp2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform