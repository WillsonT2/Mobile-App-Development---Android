package com.example.todolistapp

import androidx.compose.runtime.mutableStateListOf

class ToDoManager {
    private val toDoTasks: MutableList<String> = mutableStateListOf()


    fun getTasks(): List<String>{
        return toDoTasks.toList()
    }

    fun addTask(str: String, index: Int){
        toDoTasks.add(index, str)
    }

    fun removeTask(index: Int){
        toDoTasks.removeAt(index)
    }
}


