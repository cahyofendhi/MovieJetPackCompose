package com.bcr.moviejetpackcompose.ui.components.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.components.AppIcon
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.bcr.moviejetpackcompose.ui.theme.secondGray
import com.bcr.moviejetpackcompose.ui.theme.white

@Composable
fun AppField(value: String,
             onValueChange: (String) -> Unit,
             hint: String = "",
             error: String? = null,
             leadingIcon: @Composable () -> Unit = {},
             trailingIcon: @Composable () -> Unit = {},
             visualTransformation: VisualTransformation = VisualTransformation.None,
             keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
) {
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.Start) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(hint,
                style = appTypography.body1.copy(secondGray)) },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
                onSend = { focusManager.clearFocus() },
                onGo = { focusManager.clearFocus() }
            ),
            modifier = Modifier
                .border(
                    BorderStroke(
                        1.dp,
                        brush = Brush.horizontalGradient(listOf(primaryBlack, primaryBlack))
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .fillMaxWidth(),
            textStyle = appTypography.body1,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            singleLine = true,
            shape = RectangleShape,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = white,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )

        error?.let {
            if (it.isNotEmpty()) {
                Text(
                    text = it,
                    color = Color.Red,
                    style = appTypography.body2,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }

}

@Composable
fun PasswordField(value: String,
                  error: String? = null,
                  onValueChange: (String) -> Unit,) {
    var passwordVisibility by remember { mutableStateOf(false) }

    AppField(value = value,
        onValueChange = onValueChange,
        hint = "password",
        error = error,
        leadingIcon = { AppIcon(imageVector = Icons.Filled.Lock) },
        visualTransformation = if (passwordVisibility)
            VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                AppIcon(imageVector = image)
            }
        }
    )

}