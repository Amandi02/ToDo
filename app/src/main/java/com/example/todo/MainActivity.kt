package com.example.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityAddToDoBinding
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private  lateinit var db : TodoDatabaseHelper
    private lateinit var  todosAdapter: TodoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabaseHelper(this)
        todosAdapter = TodoAdapter(db.getAllTodos(),this)

        binding.TodoRecycleView.layoutManager = LinearLayoutManager(this)
        binding.TodoRecycleView.adapter = todosAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this,AddToDoActivity::class.java) //when clicking the add button it goes to addnote activity
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        todosAdapter.refreshdata(db.getAllTodos())
    }


}