package com.example.todolistapp.todolist

import androidx.compose.runtime.mutableStateListOf

class ToDoManager {
    private val toDoTasks: MutableList<Task> = mutableStateListOf(

    )


    fun getTasks(): List<Task>{
        return toDoTasks.toList()
    }

    fun addTask(task: Task, index: Int){
        toDoTasks.add(index, task)
    }

    fun removeTask(index: Int){
        toDoTasks.removeAt(index)
    }
}


