package com.example.task7.ui.screen.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.task7.data.model.Post
import com.example.task7.data.remote.PostService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class PostUiState {
    object Loading : PostUiState()
    data class Success(val posts: List<Post>) : PostUiState()
    data class Error(val message: String) : PostUiState()
}

@Composable
fun PostListScreen() {
    var uiState by remember { mutableStateOf<PostUiState>(PostUiState.Loading) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            try {
                val posts = PostService.fetchPosts()
                uiState = PostUiState.Success(posts)
            } catch (e: Exception) {
                uiState = PostUiState.Error("Failed to load posts: ${e.localizedMessage}")
            }
        }
    }

    when (val state = uiState) {
        is PostUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is PostUiState.Success -> {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                items(state.posts) { post ->
                    PostItem(post)
                }
            }
        }
        is PostUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = state.message, color = Color.Red)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        uiState = PostUiState.Loading
                        scope.launch(Dispatchers.IO) {
                            try {
                                val posts = PostService.fetchPosts()
                                uiState = PostUiState.Success(posts)
                            } catch (e: Exception) {
                                uiState = PostUiState.Error("Failed to load posts: ${e.localizedMessage}")
                            }
                        }
                    }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}