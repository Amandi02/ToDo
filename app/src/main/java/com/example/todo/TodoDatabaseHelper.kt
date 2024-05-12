package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDatabaseHelper(context:Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME ="Todoapp.db"
        private const val DATABASE_VERSION =1
        private const val TABLE_NAME ="alltodo"

        private const val COLUMN_ID ="id"
        private const val COLUMN_TITLE ="title"
        private const val COLUMN_CONTENT ="content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery ="CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT , $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertTodo (note: Todo){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE,note.title)
            put(COLUMN_CONTENT,note.content) // this will store the content that was added to the data class

        }

        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getAllTodos():List<Todo>{
        val todoList = mutableListOf<Todo>()
        val db = readableDatabase
        val query = "SELECT* FROM $TABLE_NAME"
        val cursor =db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)) //retrive the data from the database

            val todo = Todo(id, title, content)
            todoList.add(todo)
        }

        cursor.close()
        db.close()
        return todoList

    }

    fun updateTodo(todo: Todo){
        val db =writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,todo.title)
            put(COLUMN_CONTENT,todo.content)
        }

        val whereClause ="$COLUMN_ID= ?"
        val whereArgs = arrayOf(todo.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }


    fun getTodoByID(todoId: Int):Todo{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $todoId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Todo(id,title,content)

    }

    fun deleteTodo(todoId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(todoId.toString())
        db.delete(TABLE_NAME, whereClause,whereArgs)
        db.close()
    }


}