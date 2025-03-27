package com.example.task5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.task5.data.Student
import com.example.task5.ui.theme.Task5Theme
import com.example.task5.viewmodel.StudentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.task5.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Task5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                     StudentScreen()
                }
            }
        }
    }
}

@Composable
fun StudentList(students: LiveData<List<Student>>, onDelete: (String) -> Unit, onUpdate: (String, String) -> Unit) {
    val studentList by students.observeAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, White60) // Table outline
        ) {
            Column {
                // Header Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(DarkAccent), // Header background color
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "NRP",
                        modifier = Modifier.weight(0.25f),
                        color = White60,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Name",
                        modifier = Modifier.weight(0.50f),
                        color = White60,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Action",
                        modifier = Modifier.weight(0.25f),
                        color = White60,
                        fontWeight = FontWeight.Bold
                    )
                }

                studentList.forEach { student ->
                    var isEditing by remember { mutableStateOf(false) }
                    var newName by remember { mutableStateOf(student.name) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = student.nrp,
                            modifier = Modifier.weight(0.30f),
                            fontSize = 12.sp
                        )


                        if (isEditing) {
                            OutlinedTextField(
                                value = newName,
                                onValueChange = { newName = it },
                                label = { Text("Name") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                modifier = Modifier.weight(0.50f),
                                singleLine = false,
                                maxLines = 2,
                                textStyle = TextStyle(fontSize = 14.sp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = White87,
                                    focusedLabelColor = White87,
                                    unfocusedLabelColor = White60,
                                    unfocusedBorderColor = White60,
                                    cursorColor = White87,
                                )
                            )
                        } else {
                            Text(
                                text = student.name,
                                modifier = Modifier
                                    .weight(0.50f)
                                    .padding(end = 8.dp),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            )
                        }


                        Column(modifier = Modifier.weight(0.20f), verticalArrangement = Arrangement.Center) {
                            if (isEditing) {
                                Button(
                                    onClick = {
                                        onUpdate(student.nrp, newName)
                                        isEditing = false
                                    },
                                    modifier = Modifier
                                        .size(width = 60.dp, height = 30.dp)
                                        .padding(bottom = 4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = GreenAccent),
                                    contentPadding = PaddingValues(4.dp)
                                ) {
                                    Text("Save", fontSize = 12.sp, color = White87)
                                }
                            } else {
                                Button(
                                    onClick = { isEditing = true },
                                    modifier = Modifier
                                        .size(width = 60.dp, height = 30.dp)
                                        .padding(bottom = 4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = BlueAccent),
                                    contentPadding = PaddingValues(4.dp)
                                ) {
                                    Text("Edit", fontSize = 12.sp, color = White87)
                                }
                            }

                            Button(
                                onClick = { onDelete(student.nrp) },
                                modifier = Modifier
                                    .size(width = 60.dp, height = 30.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RedAccent2),
                                contentPadding = PaddingValues(4.dp)
                            ) {
                                Text("Delete", fontSize = 12.sp, color = White87)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentScreen(viewModel: StudentViewModel = viewModel()) {
    var studentId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = studentId,
                onValueChange = { studentId = it },
                label = { Text("NRP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = White87,
                    focusedLabelColor = White87,
                    unfocusedLabelColor = White60,
                    unfocusedBorderColor = White60,
                    cursorColor = White87,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = White87,
                    focusedLabelColor = White87,
                    unfocusedLabelColor = White60,
                    unfocusedBorderColor = White60,
                    cursorColor = White87,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.addStudent(studentId, name)
                }) {
                Text("Add", color = White60)
            }

            Spacer(modifier = Modifier.height(16.dp))

            StudentList(viewModel.students, viewModel::deleteStudent, viewModel::updateStudent)
        }
    }
}

@Composable
fun GreetingPreview() {
    Task5Theme {
        StudentScreen()
    }
}