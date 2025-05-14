package com.example.todocomposeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todocomposeapp.viewmodel.TodoDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    navController: NavController,
    todoId: Int,
    viewModel: TodoDetailViewModel
) {
    val todo by viewModel.todo.collectAsState()
    val error by viewModel.error.collectAsState()
    val isFromCache by viewModel.isFromCache.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Todo Detail", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            when {
                isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Text(error ?: "", color = MaterialTheme.colorScheme.error)
                }
                todo != null -> {
                    if (isFromCache) {
                        AssistChip(
                            onClick = {},
                            label = { Text("⚠ Loaded from cache", style = MaterialTheme.typography.bodyMedium)},
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(horizontal = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(todo!!.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Status: ${if (todo!!.completed) "Completed" else "Pending"}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Text("User ID: ${todo!!.userId}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Text("ID: ${todo!!.id}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                }
                else -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}