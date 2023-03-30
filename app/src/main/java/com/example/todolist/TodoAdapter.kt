package com.example.todolist

import com.example.todolist.Todo
import android.view.View
import android.view.ViewGroup
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.icu.text.CaseMap.Title
import android.widget.TextView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import kotlin.android.synthetic.main.item_todo.view.*

class TodoAdapter (

    private val todos: MutableList<Todo>

) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        //takes the item_todo xml file so you can work with it in kotlin
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos(){
        todos.removeAll {
            todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {

        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()

        }

    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        //bind data from todolist to the views of the list
        val curTodo = todos[position]
        holder.itemView.apply {
            tvTodoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }

    }

    override fun getItemCount(): Int {
        //returns amount of items in list
        return todos.size
    }
}