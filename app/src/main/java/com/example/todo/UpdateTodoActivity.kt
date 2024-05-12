package com.example.todo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.ActivityUpdateTodoBinding

class UpdateTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTodoBinding
    private lateinit var db: TodoDatabaseHelper
    private var todoId: Int= -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabaseHelper(this)

        todoId = intent.getIntExtra("todo_id", -1)
        if (todoId == -1) {
            finish()
            return

        }
        val todo = db.getTodoByID(todoId)

        binding.updateTitleEditText.setText(todo.title)
        binding.updateContentEditText.setText(todo.content)

        binding.updateSaveButton.setOnClickListener(){
            val newTitle =binding.updateTitleEditText.text.toString()
            val newContent =binding.updateContentEditText.text.toString()
            val updateTodo = Todo(todoId, newTitle  , newContent )

            db.updateTodo(updateTodo)
            finish()
            Toast.makeText(this,"Changes saved",Toast.LENGTH_SHORT).show()


        }


    }
}