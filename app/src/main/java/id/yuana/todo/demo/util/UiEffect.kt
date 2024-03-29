package id.yuana.todo.demo.util

import androidx.navigation.NavDirections

sealed class UiEffect {
    object PopBackStack : UiEffect()
    data class Navigate(val directions: NavDirections) : UiEffect()
    data class ShowToast(val message: String) : UiEffect()
}