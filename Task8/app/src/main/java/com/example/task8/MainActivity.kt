package com.example.task8

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.example.task8.ui.theme.Task8Theme
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task8Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CameraApp()
                }
            }
        }
    }
}

@Composable
fun CameraApp() {
    val context = LocalContext.current
    val showPreview = remember { mutableStateOf(true) }
    val capturedImageUri = remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showPreview.value) {
            CameraScreen(
                onImageCaptured = { uri ->
                    capturedImageUri.value = uri
                    showPreview.value = false
                },
                onError = { error ->
                    // Handle error
                }
            )
        } else {
            capturedImageUri.value?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Captured image",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Button(
                    onClick = { showPreview.value = true }
                ) {
                    Text("Back to Camera")
                }

                Button(
                    onClick = {
                        capturedImageUri.value?.let { uri ->
                            saveImageToGallery(context, uri)
                        }
                    }
                ) {
                    Text("Save to Gallery")
                }
            }
        }
    }
}

fun saveImageToGallery(context: Context, imageUri: Uri) {
    val resolver = context.contentResolver
    val inputStream = resolver.openInputStream(imageUri) ?: return

    val imageName = "IMG_${System.currentTimeMillis()}.jpg"

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    if (uri != null) {
        val outputStream: OutputStream? = resolver.openOutputStream(uri)
        inputStream.use { input ->
            outputStream?.use { output ->
                input.copyTo(output)
            }
        }
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(uri, contentValues, null, null)
    }
}
