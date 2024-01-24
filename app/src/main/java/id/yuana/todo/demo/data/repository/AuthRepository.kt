package id.yuana.todo.demo.data.repository

import com.google.firebase.auth.FirebaseAuth
import id.yuana.todo.demo.data.model.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface AuthRepository {

    suspend fun register(email: String, password: String): Boolean

    suspend fun login(email: String, password: String): Boolean

    suspend fun alreadyLogin(): Boolean

    suspend fun logout()

    suspend fun getAuthenticatedUser(): User

    class Impl(
        private val firebaseAuth: FirebaseAuth
    ) : AuthRepository {


        override suspend fun register(email: String, password: String): Boolean {
            return suspendCoroutine { continuation ->
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

        override suspend fun login(email: String, password: String): Boolean {
            return suspendCoroutine { continuation ->
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

        override suspend fun alreadyLogin(): Boolean {
            return firebaseAuth.currentUser != null
        }

        override suspend fun logout() {
            firebaseAuth.signOut()
        }

        override suspend fun getAuthenticatedUser(): User {
            val email = firebaseAuth.currentUser?.email ?: "guest@mail.com"
            return User(email)
        }

    }
}