package id.yuana.todo.demo.ui.splash

sealed class SplashEvent {
    object OnLoad : SplashEvent()
    object OnGotoLogin : SplashEvent()
    object OnGotoHome : SplashEvent()
}