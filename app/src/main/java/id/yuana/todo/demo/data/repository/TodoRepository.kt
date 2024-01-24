package id.yuana.todo.demo.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import id.yuana.todo.demo.data.model.Todo
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface TodoRepository {

    companion object {
        private const val COLLECTION_APP = "todoapp"
        private const val COLLECTION_NAME = "todos"
    }

    suspend fun all(): List<Todo>

    suspend fun createOrUpdate(todo: Todo)

    class Impl(
        private val authRepository: AuthRepository,
        private val firebaseFirestore: FirebaseFirestore
    ) : TodoRepository {

        override suspend fun all(): List<Todo> {
            val currentUserEmail = authRepository.getAuthenticatedUser().email
            return suspendCoroutine { continuation ->
                firebaseFirestore.collection(COLLECTION_APP)
                    .document(currentUserEmail)
                    .collection(COLLECTION_NAME)
                    .get()
                    .addOnSuccessListener {
                        continuation.resume(it.documents.map { it.toObject<Todo>() ?: Todo() }
                            .toList())
                    }
                    .addOnFailureListener {
                        continuation.resume(emptyList())
                    }
            }
        }

        override suspend fun createOrUpdate(todo: Todo) {
            val currentUserEmail = authRepository.getAuthenticatedUser().email
            suspendCoroutine { continuation ->
                firebaseFirestore.collection(COLLECTION_APP)
                    .document(currentUserEmail)
                    .collection(COLLECTION_NAME)
                    .document(todo.id)
                    .set(todo)
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

    }
}