package com.example.task7.data.remote

import com.example.task7.data.model.Post
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getPosts(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
    }
}