package com.bcr.moviejetpackcompose.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcr.moviejetpackcompose.utils.emailValidation
import com.bcr.moviejetpackcompose.utils.passwordValidation
import kotlinx.coroutines.flow.*
import java.util.*

enum class Status { INIT, LOADING, SUCCESS, ERROR  }

data class InputWrapper(
    val value: String = "",
    val error: String? = ""
)

data class LoginState(
    val email: InputWrapper = InputWrapper(),
    val password: InputWrapper = InputWrapper(),
    val valid: Boolean = false,
    val status: Status = Status.INIT,
) {
    fun toUiState(): LoginState = LoginState(
            email = email,
            password = password,
            valid = email.error == null && password.error == null,
            status = status,
        )
}

class LoginViewModel: ViewModel() {

    private val viewModelState = MutableStateFlow(LoginState())

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun updateEmail(email: String) {
        val error = emailValidation(email)
        viewModelState.update { it.copy(email = InputWrapper(email, error)) }
    }

    fun updatePassword(password: String) {
        val error = passwordValidation(password)
        viewModelState.update { it.copy(password = InputWrapper(password, error)) }
    }

    fun submit() {
        viewModelState.update { it.copy(status = Status.LOADING) }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                viewModelState.update { it.copy(status = Status.SUCCESS) }
            }
        }, 5000)
    }

}