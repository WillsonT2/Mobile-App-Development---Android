package com.example.todolistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Task(
    val taskName: String,
    var isPriority: Boolean,
) {
    var observableIsPriority by mutableStateOf(isPriority)

    fun togglePriority(){
        observableIsPriority = !observableIsPriority
    }
}