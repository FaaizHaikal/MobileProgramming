package com.example.task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.task2.ui.theme.Task2Theme
import com.example.task2.ui.theme.White87
import com.example.task2.ui.theme.White60


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Task2Theme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text= "Simple Calculator", color = White87, fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Number 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White87,
                focusedLabelColor = White87,
                unfocusedLabelColor = White60,
                unfocusedBorderColor = White60,
                cursorColor = White87,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Number 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White87,
                focusedLabelColor = White87,
                unfocusedLabelColor = White60,
                unfocusedBorderColor = White60,
                cursorColor = White87,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { result = calculate("+", num1, num2) }) {
                Text("+", color = White60)
            }

            Button(onClick = { result = calculate("-", num1, num2) }) {
                Text("-", color = White60)
            }

            Button(onClick = { result = calculate("*", num1, num2) }) {
                Text("*", color = White60)
            }

            Button(onClick = { result = calculate("/", num1, num2) }) {
                Text("/", color = White60)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Result: $result",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Task2Theme {
        Calculator()
    }
}

fun calculate(operator: String, num1: String, num2: String): String {
    val number1 = num1.toDoubleOrNull()
    val number2 = num2.toDoubleOrNull()
    if (number1 == null || number2 == null) {
        return "Error! Required Inputs"
    }
    return when (operator) {
        "+" -> (number1 + number2).toString()
        "-" -> (number1 - number2).toString()
        "*" -> (number1 * number2).toString()
        "/" -> if (number2 != 0.0) {
            (number1 / number2).toString()
        } else {
            "Error! Division by zero"
        }
        else -> "Error! Unknown operation"
    }
}