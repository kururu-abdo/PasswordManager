package com.kururu.password_manager.views

import android.app.Application
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kururu.password_manager.Events.AppEvents
import com.kururu.password_manager.State.AppStates
import com.kururu.password_manager.ui.theme.Purple700
import com.kururu.password_manager.ui.theme.backGroundColor
import com.kururu.password_manager.ui.theme.textFieldColor
import com.kururu.password_manager.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LockScreen(
    navController: NavController,
    viewModel: MainViewModel =
        MainViewModel(LocalContext.current.applicationContext as Application)

){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var textError by remember { mutableStateOf(false) }
    var textLabel by remember { mutableStateOf("Enter password") }



    val keyboardController = LocalSoftwareKeyboardController.current

   Column(
       modifier = Modifier.fillMaxSize(),
       verticalArrangement = Arrangement.Center ,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {


    Box(
//        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PassswrdField(
            label ={ Text(text = textLabel)},
            isError = textError,
            value = text.text, onValueChange = {
            if (it.length <= 4) text = TextFieldValue(it)
        } ,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) ,

            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }


            )
        )

    }



           Box(contentAlignment = Alignment.CenterEnd) {
               Button(
                   modifier = Modifier.background(Purple700),
                   onClick = {
                       if (text.text.isEmpty()){
textError=true
    textLabel ="please Enter Password"
                       }else {
                           viewModel.onEvent(AppEvents.Register(text.text ) ,navController )

                       }
                   }) {
                   Text(text = "Save")
               }
           }

        }

}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LockLoginScreen(
    navController: NavController,
    autheenticating:Boolean =false ,
            viewModel: MainViewModel =
            MainViewModel(LocalContext.current.applicationContext as Application)

    ,  password:String="" ,email:String=""
){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var textError by remember { mutableStateOf(false) }
    var textLabel by remember { mutableStateOf("Enter password") }
    val keyboardController = LocalSoftwareKeyboardController.current
//    LaunchedEffect(viewModel.uiState.isPassed) {
//
//
//        if (viewModel.uiState.isPassed){
//            viewModel.onEvent(AppEvents.FirstTimeEvent() ,navController)
//        }else {
//            navController.navigate("/")
//        }
//
//    }
    Column(
        modifier=Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center
    ) {


        Box(
//            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PassswrdField(
                isError = textError,
                label ={ Text(text = textLabel)},
                value = text.text, onValueChange = {
                if (it.length <= 4) text = TextFieldValue(it)
            }  ,

                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) ,

                keyboardActions = KeyboardActions(onDone = {
      if (text.text.isEmpty()){
          textError=true
          textLabel ="please Enter Password"

      }else{
          keyboardController?.hide()
          viewModel.onEvent(AppEvents.Login(text.text , email  ,password) ,navController)
      }
                }
            )
            )

        }



//        if(!autheenticating) {
//            Box(contentAlignment = Alignment.CenterEnd) {
//                Button(
//                    modifier = Modifier.background(Purple700),
//                    onClick = { /*TODO*/ }) {
//                    Text(text = "Save")
//                }
//            }
//        }
    }

}

@Composable
fun PassswrdField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions ,
    label: @Composable (() -> Unit),

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

        keyboardActions = keyboardActions
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
    keyboardOptions:KeyboardOptions=KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

    label: @Composable (() -> Unit),

    keyboardActions: KeyboardActions
) {
    TextField(
        value = value,
        label = label,
        enabled = enabled,
        isError = isError,

        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
keyboardActions = keyboardActions,
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
        shape = RoundedCornerShape(8.dp),



        colors = TextFieldDefaults.textFieldColors(
            backgroundColor =  textFieldColor,
            cursorColor = Color.Black,
            disabledLabelColor =  textFieldColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
    )
}