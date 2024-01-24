package id.yuana.todo.demo.ui.home

import id.yuana.todo.demo.data.model.Todo

data class HomeState(
    val todos: List<Todo> = emptyList(),
)
