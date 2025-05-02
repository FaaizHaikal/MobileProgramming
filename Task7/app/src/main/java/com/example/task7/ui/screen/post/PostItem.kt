package com.example.task7.ui.screen.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task7.data.model.Post

@Composable
fun PostItem(post: Post) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier
            .background(Color.Gray)
            .padding(16.dp)
        ) {
            PostField(label = "User ID", value = post.userId.toString())
            PostField(label = "Post ID", value = post.id.toString())
            PostField(label = "Title", value = post.title)
            PostField(label = "Body", value = post.body)
        }
    }
}

@Composable
fun PostField(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
        Text(text = value, style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp))
    }
}