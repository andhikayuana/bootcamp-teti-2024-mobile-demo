package id.yuana.todo.demo.ui.home

sealed class HomeEvent {
    object OnLogoutClick : HomeEvent()
    object OnLoadTodos : HomeEvent()
    object OnCreateTodo : HomeEvent()
}