package com.example.todocomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todocomposeapp.data.local.TodoDao
import com.example.todocomposeapp.data.local.TodoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val todoDao: TodoDao,
    private val todoId: Int
) : ViewModel() {

    private val _todo = MutableStateFlow<TodoEntity?>(null)
    val todo: StateFlow<TodoEntity?> = _todo

    init {
        viewModelScope.launch {
            _todo.value = todoDao.getAll().find { it.id == todoId }
        }
    }

    class Factory(private val todoDao: TodoDao, private val todoId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TodoDetailViewModel(todoDao, todoId) as T
        }
    }
}