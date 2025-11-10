package com.example.todolistapp

import androidx.compose.runtime.mutableStateListOf

class ToDoManager {
    private val toDoTasks: MutableList<String> = mutableStateListOf("" +
            "Paint cabinet",
            "Buy groceries",
            "Clean my room",
            "Do the laundry",
            "Call mom",
            "Stop by post office",
            "Pay water bill",
            "Wash dirty dishes",
            "Vacuum carpet",
            "Fix leaking faucet",
            "Attend meeting on Friday",
            "Buy tickets for game",
            "Pick up bread at bakery",
            "Walk dog",
            "Do my homework",
            "Brush teeth"
    )

    fun getTasks(): List<String>{
        return toDoTasks.toList()
    }

    fun addTask(str: String){
        toDoTasks.add(0, str)
    }

}


