package com.kururu.password_manager.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kururu.password_manager.R

@Composable
fun  AccountDetails(
    navController:NavController,
    email:String , password:String ){
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val mContext = LocalContext.current

    Scaffold (

        topBar = {
            TopAppBar(


            backgroundColor = Color.Transparent ,
            elevation = 0.0.dp


            ) {
                
                IconButton(onClick = { navController.navigate("/")  }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )


                }
                Text(text = "Account Details")
            }

        }  ,
        content = {
            Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center

                ) {
                Column (modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally ,
                    verticalArrangement = Arrangement.Center
                    ){
                    Row() {
                        Text(email)
                        IconButton(onClick = {


                            clipboardManager.setText(AnnotatedString((email)))


                           Toast.makeText(mContext , "Coppied" ,Toast.LENGTH_LONG).show()


                        }) {
                            Icon(   painter = painterResource(id = R.drawable.ic_baseline_content_copy_24),
                                tint = Color(0xFF3E92CC) ,
                                contentDescription = "" )
                        }
                    }

                    Row() {
                        Text(password)

                        Text("")
                        IconButton(onClick = {


                            clipboardManager.setText(AnnotatedString((password)))
                            Toast.makeText(mContext , "Coppied" ,Toast.LENGTH_LONG).show()

                        }) {
                            Icon(   painter = painterResource(id = R.drawable.ic_baseline_content_copy_24),
                                tint = Color(0xFF3E92CC) ,
                                contentDescription = "" )
                        }
                    }
                }


            }
        }
            )

}