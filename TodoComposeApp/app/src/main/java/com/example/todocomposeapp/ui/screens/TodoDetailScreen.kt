package com.example.todocomposeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todocomposeapp.viewmodel.TodoDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    navController: NavController,
    todoId: Int,
    viewModel: TodoDetailViewModel
) {
    val todo = viewModel.todo.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Todo Detail") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            todo.value?.let {
                Text("Title:", style = MaterialTheme.typography.labelSmall)
                Text(it.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Status: ${if (it.completed) "Completed" else "Pending"}", style = MaterialTheme.typography.bodyLarge)
                Text("User ID: ${it.userId}", style = MaterialTheme.typography.bodyLarge)
                Text("ID: ${it.id}", style = MaterialTheme.typography.bodyLarge)
            } ?: Text("Todo not found", modifier = Modifier.padding(16.dp))
        }
    }
}