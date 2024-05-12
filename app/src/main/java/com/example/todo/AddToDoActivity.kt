package com.example.todo


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivityAddToDoBinding
import com.example.todo.databinding.ActivityAddToDoBinding

class AddToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddToDoBinding
    private lateinit var db:TodoDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val todo = Todo(0,title,content)
            db.insertTodo(todo)
            finish()
            Toast.makeText(this,"Todo saved",Toast.LENGTH_SHORT).show()
        }
    }
}
