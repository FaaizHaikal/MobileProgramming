package com.example.task5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task5.data.Student
import com.example.task5.data.StudentDbHelper

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = StudentDbHelper(application)

    private val _students = MutableLiveData<List<Student>>(dbHelper.getAllStudents())
    val students: LiveData<List<Student>> = _students

    fun addStudent(studentId: String, name: String) {
        dbHelper.insertStudent(studentId, name)
        _students.value = dbHelper.getAllStudents()
    }

    fun deleteStudent(studentId: String) {
        dbHelper.deleteStudent(studentId)
        _students.value = dbHelper.getAllStudents()
    }

    fun updateStudent(studentId: String, newName: String) {
        dbHelper.updateStudent(studentId, newName)
        _students.value = dbHelper.getAllStudents() // Refresh student list
    }
}