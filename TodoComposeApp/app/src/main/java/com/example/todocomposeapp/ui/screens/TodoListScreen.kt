package com.example.todocomposeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todocomposeapp.viewmodel.TodoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(navController: NavController, viewModel: TodoListViewModel = viewModel()) {
    val todos = viewModel.todos.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()
    val isFromCache = viewModel.isFromCache.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Todo List", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary)
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            when {
                isLoading.value -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                error.value != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Error: ${error.value}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> Column {
                    if (isFromCache.value) {
                        AssistChip(
                            onClick = {},
                            label = { Text("⚠ Loaded from cache", style = MaterialTheme.typography.bodyMedium)},
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(todos.value) { todo ->
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth()
                                    .clickable { navController.navigate("detail/${todo.id}") },
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(todo.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                                    Text(
                                        if (todo.completed) "✔ Completed" else "✘ Pending",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}