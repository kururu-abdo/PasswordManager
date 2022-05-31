package com.kururu.password_manager.views

import android.app.Application
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kururu.password_manager.R
import com.kururu.password_manager.data.models.AccountData
import com.kururu.password_manager.ui.theme.backGroundColor

import com.kururu.password_manager.viewmodels.MainViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun  Home(navController: NavController, onTryShowDetails: () -> Unit ,


          viewModel: MainViewModel =
              MainViewModel(LocalContext.current.applicationContext as Application)
          ) {
    val swipeRefreshState = rememberSwipeRefreshState(false)
    var searchTxt = remember {
        mutableStateOf("")
    }
    val allAccount by viewModel.allProducts.observeAsState(listOf())

    val items by viewModel.searchResults.observeAsState(listOf())

var mContext =LocalContext.current


    val keyboardController = LocalSoftwareKeyboardController.current


    Scaffold(
        backgroundColor =backGroundColor,
        topBar = {
            TopAppBar(
                title = {Text("passwords")},
                Modifier.background(Color.Transparent),
                actions = {
                    IconButton(

                        onClick = {
                            navController.navigate("/dev")
                        }) {
                        Icon(Icons.Outlined.Info , contentDescription = "Notifications")
                    }
                },
                backgroundColor = Color.Transparent ,
                elevation = 0.0.dp
            )


        } ,
        content = {


            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    value = searchTxt.value,
                    onValueChange = { searchTxt.value = it },


                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)

//                       .background(color = Color(0xFFC4C4C4)) ,

                    , shape = RoundedCornerShape(8.dp),



                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFf2f2f2),
                        cursorColor = Color.Black,
                        disabledLabelColor = Color(0xFFC4C4C4),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                      viewModel.findAccount(searchTxt.value)
                        navController.navigate("/search?query=${searchTxt.value}")
                    }) ,
                    trailingIcon = {


                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )


                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Saved Passwords", modifier = Modifier.padding(10.dp))
                Spacer(modifier = Modifier.height(15.dp))


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    ) {
                        if (allAccount.isEmpty()){
                            Box(modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center

                            ) {
                                Column (modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally ,
                                    verticalArrangement = Arrangement.Center
                                ){

                                    Image(painter = painterResource(id = R.drawable.padlock),

                                        contentDescription ="" )
                                    Text(text = "No Saved Passwords")
                                }
                            }
                        }
                       else {
                            SwipeRefresh(
                                state = swipeRefreshState,
                                onRefresh = {

                                },
                                indicator = { state, trigger ->
                                    SwipeRefreshIndicator(
                                        // Pass the SwipeRefreshState + trigger through
                                        state = state,
                                        refreshTriggerDistance = trigger,
                                        // Enable the scale animation
                                        scale = true,
                                        // Change the color and shape
                                        backgroundColor = MaterialTheme.colors.primary,
                                        shape = MaterialTheme.shapes.small,
                                    )
                                }
                            ) {

                                Column(
                                    modifier = Modifier.padding(15.dp)
                                ) {

                                    val list = listOf(
                                        "A", "B", "C", "D"
                                    ) + ((0..100).map { it.toString() })
                                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                                        items(count = allAccount.size, itemContent = { item ->


                                            PasswordContainer(
                                                navController,
                                                allAccount[item],
                                                onTryShowDetails
                                            )


                                        })
                                    }
                                }
                            }
                        }
                    }


            }    },
        floatingActionButton = { FloatingActionButton(

            onClick = {

                navController.navigate("/new")
            },
            backgroundColor =  Color(0xFF3E92CC)
        ) {
            Icon(Icons.Default.Add, contentDescription = "" ,
                tint = Color.White
            )
        }}


    )


}


@Composable
fun PasswordContainer(
    navController: NavController,
    accountData: AccountData,
    checkFingrPrint:()->Unit ,

    viewModel: MainViewModel =
        MainViewModel(LocalContext.current.applicationContext as Application)
){
    val mContext = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            ,
        elevation = 5.dp
    ){
Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment=Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
    var expanded by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false)  }
    Image(
        painterResource(accountData.icon),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(20.dp ,20.dp)
    )
    Column {
      Text(text = accountData.email)
        Text(text = accountData.comment)
    }
 Box(
     modifier = Modifier.width(20.dp)
 ) {
    IconButton(onClick = { expanded=  true }) {
        Icon(imageVector = Icons.Outlined.MoreVert,
            contentDescription = "menue")
    }
     DropdownMenu(
         expanded = expanded,
         onDismissRequest = { expanded = false }
     ) {
         DropdownMenuItem(onClick = {
             expanded = false
             navController.navigate("/details?password=${accountData.password}&email=${accountData.email}")

         }) {
             Text("Details")
         }
         DropdownMenuItem(onClick = { expanded = false
             openDialog.value =true
         }) {
             Text("Delete")
         }

     }
 }
    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Icon(imageVector = Icons.Outlined.Delete,
                    tint = Color.Red                    ,
                    contentDescription = "delete")
            },
            text = {
                Text("Do You Want Do Delete this? ")
            },
            confirmButton = {
                Button(

                    onClick = {
                        openDialog.value = false

                        //delete item from database
viewModel.deleteProduct(accountData.id)
                        Toast.makeText(mContext , "Deleted!" ,Toast.LENGTH_LONG).show()


                    }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.Transparent
                    ),

                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Cancel")
                }
            }
        )
    }
}
    }
}