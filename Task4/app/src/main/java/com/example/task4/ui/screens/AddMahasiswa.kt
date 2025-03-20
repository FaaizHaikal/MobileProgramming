package com.example.task4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import com.example.task4.ui.theme.White87
import com.example.task4.ui.theme.White60

@Composable
fun AddMahasiswa() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var inputNim by remember { mutableStateOf("") }
        var inputName by remember { mutableStateOf("") }
        var resultNim by remember { mutableStateOf("") }
        var resultName by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Add Mahasiswa", color = White87, fontSize = 32.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputNim,
                onValueChange = { inputNim = it },
                label = { Text("NIM") },
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
                value = inputName,
                onValueChange = { inputName = it },
                label = { Text("Nama") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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

            Button(onClick = {
                resultNim = inputNim;
                resultName = inputName;
            }) {
                Text("Submit", color = White60)
            }


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "NIM: $resultNim",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Nama: $resultName",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}