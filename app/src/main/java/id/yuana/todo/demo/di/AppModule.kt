package id.yuana.todo.demo.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.yuana.todo.demo.data.repository.AuthRepository
import id.yuana.todo.demo.data.repository.TodoRepository

object AppModule {

    private val firebaseAuth = Firebase.auth
    private val firebaseFirestore = Firebase.firestore
    private val authRepository = AuthRepository.Impl(firebaseAuth)
    private val todoRepository = TodoRepository.Impl(authRepository, firebaseFirestore)

    fun provideAuthRepository(): AuthRepository {
        return authRepository
    }

    fun provideTodoRepository(): TodoRepository {
        return todoRepository
    }
}