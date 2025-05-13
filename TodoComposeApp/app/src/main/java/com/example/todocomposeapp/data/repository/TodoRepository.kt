package com.example.todocomposeapp.data.repository

import com.example.todocomposeapp.data.local.TodoDao
import com.example.todocomposeapp.data.local.TodoEntity
import com.example.todocomposeapp.data.remote.TodoApiService

class TodoRepository(
    private val api: TodoApiService,
    private val dao: TodoDao
) {
    suspend fun fetchTodos(): List<TodoEntity> {
        return try {
            val todos = api.getTodos()
            val entities = todos.map {
                TodoEntity(
                    id = it.id,
                    userId = it.userId,
                    title = it.title,
                    completed = it.completed
                )
            }
            dao.insertAll(entities)
            entities
        } catch (e: Exception) {
            dao.getAll() // fallback to cache
        }
    }
}