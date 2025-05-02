package com.example.task7.data.remote

import com.example.task7.data.model.Post

object PostService {
    suspend fun fetchPosts(): List<Post> {
        return ApiClient.getPosts()
    }
}