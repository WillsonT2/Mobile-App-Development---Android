package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListApp()
        }
    }
}

@Composable
fun ToDoListApp() {
    val toDoManager = ToDoManager()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showAlert by remember { mutableStateOf(false) }
    var savedIndex by remember { mutableIntStateOf(-1) }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Surface(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ){
            if (showAlert){
                AlertDialog(
                    onDismissRequest = {

                    },
                    title = {
                        Text(text = "Confirmation")
                    },
                    text = {
                        Text(text = "Are you sure you want to delete?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val removed = toDoManager.getTasks()[savedIndex]
                                toDoManager.removeTask(savedIndex)
                                scope.launch {
                                    val result: SnackbarResult = snackbarHostState.showSnackbar(
                                        message = "Task Completed",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )
                                    when (result){
                                        SnackbarResult.ActionPerformed ->{
                                            toDoManager.addTask(removed, savedIndex)
                                        }
                                        SnackbarResult.Dismissed ->{

                                        }
                                    }
                                }
                                showAlert = false
                            }
                        ) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showAlert = false
                            }
                        ) {
                            Text(text = "Dismiss")
                        }
                    }
                )
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(16.dp)
                ){
                    var enteredText by remember { mutableStateOf("") }
                    TextField(
                        value = enteredText,
                        onValueChange = {
                            enteredText = it
                        },
                        singleLine = true,
                        label = { Text(text = "Add new task") },
                        placeholder = { Text(text = "Type your task") },
                        textStyle = TextStyle(
                            color = Color(230, 160, 70),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.weight(1.0F)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            toDoManager.addTask(enteredText, 0)
                            enteredText = ""
                        }
                    ) {
                        Text(
                            text = "Add"
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    FilledIconButton(
                        onClick = {

                        }
                    ) {
//                        Icon(
//                            painter = painterResource()
//                        )
                    }
                }
                if (toDoManager.getTasks().isEmpty()){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Add your first task!",
                            color = Color(230, 160, 70),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }else {
                    LazyColumn(
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(toDoManager.getTasks().size){index ->
                            TextRow(toDoManager.getTasks()[index], modifier = Modifier.clickable(
                                onClick = {
                                    showAlert = true
                                    savedIndex = index
                                }
                            ))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextRow(taskText: String, modifier: Modifier){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(230, 160, 70),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = modifier
    ) {
        Text(
            text = taskText,
            fontSize = 28.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}
