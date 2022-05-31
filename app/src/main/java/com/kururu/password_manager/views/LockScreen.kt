package com.kururu.password_manager.views

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LockScreen(
    autheenticating:Boolean =false

){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
PassswrdField(value = text.text, onValueChange = {
    if (it.length<=4) text= TextFieldValue(it)
}
)
        }

}

@Composable
fun PassswrdField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    var passwordVisible = remember{
        mutableStateOf(false)
    }

    PasswordTextField(
        value = value,
        onValueChange = onValueChange,
        passwordVisible = passwordVisible.value,
        onTogglePasswordVisibility = { passwordVisible.value = !passwordVisible.value },
        modifier = modifier,
        enabled = enabled,

        isError = isError,
        keyboardOptions = keyboardOptions,
        label = label,
        colors = colors,
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    OutlinedTextField(
        value = value,
        label = label,
        enabled = enabled,
        isError = isError,
        singleLine = true,

        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(
                onClick = onTogglePasswordVisibility,
            ) {
                Crossfade(
                    targetState = passwordVisible,
                ) { visible ->
                    Icon(
                        painter = painterResource(
                            id = if (visible) {
                                com.kururu.password_manager.R.drawable.ic_visibility_on
                            } else {
                                com.kururu.password_manager.R.drawable.ic_visibility_off
                            }
                        ),
                        contentDescription = "show Password"
                    )
                }
            }
        },
        onValueChange = onValueChange,
        modifier = modifier,
        colors = colors,
    )
}