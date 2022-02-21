package com.bcr.moviejetpackcompose.ui.components.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.bcr.moviejetpackcompose.ui.theme.white

@Composable
fun SearchView(state: MutableState<TextFieldValue>, onSubmit: () -> Unit) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        placeholder = { Text("Search...", style = appTypography.body1) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSubmit()
            }
        ),
        modifier = Modifier
            .border(
                BorderStroke(1.dp, brush = Brush.horizontalGradient(listOf(primaryBlack, primaryBlack))),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth(),
        textStyle = appTypography.body1,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp),
                tint = primaryBlack
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        tint = primaryBlack
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = white,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
    )
}
