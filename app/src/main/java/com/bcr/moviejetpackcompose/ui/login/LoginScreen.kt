package com.bcr.moviejetpackcompose.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.core.viewmodels.LoginState
import com.bcr.moviejetpackcompose.core.viewmodels.LoginViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.Status
import com.bcr.moviejetpackcompose.ui.components.AppButton
import com.bcr.moviejetpackcompose.ui.components.AppIcon
import com.bcr.moviejetpackcompose.ui.components.field.AppField
import com.bcr.moviejetpackcompose.ui.components.field.PasswordField
import com.bcr.moviejetpackcompose.ui.theme.*
import com.bcr.moviejetpackcompose.utils.LoadingDialog

private lateinit var uiState: State<LoginState>

@Composable
fun LoginScreen(viewmodel: LoginViewModel, onNext: () -> Unit) {
    uiState = viewmodel.uiState.collectAsState()

    Scaffold(
        backgroundColor = white,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Login",
                style = appTypography.h5,
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
            )

            EmailTextField(onValueChange = { value -> viewmodel.updateEmail(value) })

            Box(modifier = Modifier.padding(bottom = 16.dp))

            PasswordField(value = uiState.value.password.value,
                error = uiState.value.password.error,
                onValueChange = { value -> viewmodel.updatePassword(value) })

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AppButton(title = "Submit", enable = uiState.value.valid) {
                viewmodel.submit()
            }

            when (uiState.value.status) {
                Status.LOADING -> LoadingDialog()
                Status.SUCCESS -> onNext()
                Status.ERROR -> {
                    Toast.makeText(
                        LocalContext.current,
                        "Login Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }

        }
    }
}

@Composable
private fun EmailTextField(onValueChange: (String) -> Unit) {

    AppField(value = uiState.value.email.value,
        onValueChange = onValueChange,
        hint = "email@gmail.com",
        error = uiState.value.email.error,
        leadingIcon = { AppIcon(imageVector = Icons.Filled.Email) }
    )

}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(LoginViewModel(), {})
}