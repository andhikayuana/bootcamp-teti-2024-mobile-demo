package id.yuana.todo.demo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.yuana.todo.demo.data.repository.AuthRepository
import id.yuana.todo.demo.util.UiEffect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val loginState: MutableLiveData<LoginState> by lazy {
        MutableLiveData<LoginState>(LoginState())
    }
    val uiEffect: MutableLiveData<UiEffect> by lazy {
        MutableLiveData<UiEffect>()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                loginState.postValue(loginState.value?.copy(email = event.email))
            }

            LoginEvent.OnGotoRegisterClick -> {
                uiEffect.postValue(UiEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()))
            }

            LoginEvent.OnLoginClick -> {
                viewModelScope.launch {
                    try {
                        val result = authRepository.login(
                            email = loginState.value?.email.orEmpty(),
                            password = loginState.value?.password.orEmpty()
                        )

                        if (result) {
                            uiEffect.postValue(UiEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment()))
                        } else {
                            throw Exception("Your email or password didn't match!")
                        }
                    } catch (e: Exception) {
                        uiEffect.postValue(
                            UiEffect.ShowToast(
                                e.message ?: "Oops, something went wrong!"
                            )
                        )
                    }

                }
            }

            is LoginEvent.OnPasswordChange -> {
                loginState.postValue(loginState.value?.copy(password = event.password))
            }
        }
    }
}