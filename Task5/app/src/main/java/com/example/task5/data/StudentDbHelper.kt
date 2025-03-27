package com.example.task5.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "nrp"
        private const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID TEXT PRIMARY KEY, $COLUMN_NAME TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertStudent(studentId: String, name: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, studentId)
            put(COLUMN_NAME, name)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllStudents(): List<Student> {
        val studentList = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            studentList.add(Student(id, name))
        }
        cursor.close()
        db.close()
        return studentList
    }

    fun deleteStudent(studentId: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(studentId))
        db.close()
    }

    fun updateStudent(studentId: String, newName: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, newName)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(studentId))
        db.close()
    }

    }