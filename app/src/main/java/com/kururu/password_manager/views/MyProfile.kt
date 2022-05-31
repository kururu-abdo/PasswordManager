package com.kururu.password_manager.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kururu.password_manager.R

@Composable
fun MyProfile(navController: NavController){

    val context = LocalContext.current
    val whatsAppIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/+249998541587")) }
    val githubIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kururu-abdo")) }
    val gmailIntent = remember { Intent(Intent.ACTION_VIEW,    Uri.parse("mailto:" + "wdalhajana50@gmail.com")) }


    Uri.parse("mailto:" + "your_email")
    Scaffold (
        topBar = { TopAppBar(
            title = {Text("About Dev")} ,
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )



                }
            } ,

            backgroundColor = Color.Transparent ,
            elevation = 0.0.dp
        )

        } ,
        content = {
            Column(

                modifier = Modifier.fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Spacer(modifier = Modifier.height(10.dp))

                Image(painter = painterResource(id = R.drawable.me), contentDescription ="" ,
                modifier = Modifier.size(200.dp ,200.dp)
                    )
                Spacer(modifier = Modifier.height(10.dp)) 
                
                Card( modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

                    ,
                    
                    elevation = 10.dp,
                    
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()

                        .padding(10.dp)
                    ) {
                        Column(                 horizontalAlignment = Alignment.Start ,
                        ) {
                            Text(text = "Abdurrahim Hassan Kururu", style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 15.0.sp))
                            Text(text = "wdalhajana50@gmail.com", style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 15.0.sp))
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Languages & Skills:" , style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 15.0.sp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "- Kotlin.")
                        Text(text = "- Java.")
                        Text(text = "- Dart.")
                        Text(text = "- Flutter.")
                        Text(text = "- Firebase.")
                        Text(text = "- SQL.")

                        Spacer(modifier = Modifier.height(30.dp))
                        Text(text = "Contacts:" , style = TextStyle(fontWeight = FontWeight.Bold , fontSize = 15.0.sp))

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly

                            ,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = {
                              context.startActivity(whatsAppIntent)
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.whatsapp),
                                    contentDescription = "Refresh Button"
                                )
                            }

                            IconButton(onClick = {
                                context.startActivity(gmailIntent)
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.gmail),
                                    contentDescription = "Refresh Button"
                                )
                            }


                            IconButton(onClick = {
                                context.startActivity(githubIntent)
                            }) {
                                Image(
                                    painter = painterResource(id = R.drawable.github),
                                    contentDescription = "Refresh Button"
                                )
                            }
                        }









                    }
                }

            }
        }
            )


}