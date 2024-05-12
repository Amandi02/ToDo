package com.example.todo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private var todos:List<Todo>,context: Context):
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val db:TodoDatabaseHelper = TodoDatabaseHelper(context)

    class TodoViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.TitleTextView)
        val contentTextView : TextView = itemView.findViewById(R.id.ContentTextView)
        val updateButton : ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.titleTextView.text= todo.title
        holder.contentTextView.text= todo.content

        holder.updateButton.setOnClickListener(){
            val intent = Intent(holder.itemView.context, UpdateTodoActivity::class.java).apply {
                putExtra("todo_id", todo.id)
            }

            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener(){
            db.deleteNote(note.id)
            refreshdata(db.getAllNotes())
        }

    }

    fun refreshdata(newTodos : List<Todo>){
        todos = newTodos
        notifyDataSetChanged()
    }
}