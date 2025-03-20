package com.example.task4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import com.example.task4.ui.theme.Task4Theme
import com.example.task4.ui.screens.AddMahasiswa
import com.example.task4.ui.theme.White60

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task4Theme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "greeting") {
                    composable("greeting") { Greeting(navController) }
                    composable("addMahasiswa") { AddMahasiswa() }
                }
            }
        }
    }
}

@Composable
fun Greeting(navController: NavController) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var showMessage by remember { mutableStateOf(false) }
        val context = LocalContext.current

        LaunchedEffect(showMessage) {
            if (showMessage) {
                Toast.makeText(context, "Hello, Senopati!", Toast.LENGTH_SHORT).show()
                delay(2000)
                navController.navigate("addMahasiswa")
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            if (!showMessage) {
                Button(
                    onClick = { showMessage = true },
                    shape = CircleShape,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Click Me!",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        color = White60,
                        fontSize = 24.sp
                    )
                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Task4Theme {
        Greeting(rememberNavController())
    }
}