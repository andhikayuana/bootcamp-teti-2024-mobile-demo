package id.yuana.todo.demo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.yuana.todo.demo.data.repository.AuthRepository
import id.yuana.todo.demo.util.UiEffect
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val registerState: MutableLiveData<RegisterState> by lazy {
        MutableLiveData<RegisterState>(RegisterState())
    }

    val uiEffect: MutableLiveData<UiEffect> by lazy {
        MutableLiveData<UiEffect>()
    }


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnEmailChange -> {
                registerState.postValue(registerState.value?.copy(email = event.email))
            }

            is RegisterEvent.OnPasswordChange -> {
                registerState.postValue(registerState.value?.copy(password = event.password))
            }

            is RegisterEvent.OnPasswordConfirmChange -> {
                registerState.postValue(registerState.value?.copy(passwordConfirm = event.passwordConfirm))
            }

            RegisterEvent.OnRegisterClick -> {
                viewModelScope.launch {
                    try {
                        authRepository.register(
                            email = registerState.value?.email.orEmpty(),
                            password = registerState.value?.password.orEmpty()
                        )
                        uiEffect.postValue(UiEffect.PopBackStack)
                    } catch (e: Exception) {
                        uiEffect.postValue(
                            UiEffect.ShowToast(
                                e.message ?: "Oops, something went wrong!"
                            )
                        )
                    }
                }
            }
        }
    }

}