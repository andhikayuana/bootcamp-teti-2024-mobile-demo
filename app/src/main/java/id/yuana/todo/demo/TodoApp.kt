package id.yuana.todo.demo

import android.app.Application
import id.yuana.todo.demo.di.AppModule

class TodoApp : Application() {

    lateinit var appModule: AppModule

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule
    }
}