package com.kururu.password_manager.views

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.kururu.password_manager.ui.theme.backGroundColor
import com.kururu.password_manager.viewmodels.MainViewModel

@Composable
fun  SearchResultContainer(navController: NavController ,

                           query:String ,
                           onTryShowDetails: () -> Unit ,
                           viewModel: MainViewModel =
                               MainViewModel(LocalContext.current.applicationContext as Application)
                           ){
    val mContext = LocalContext.current

    val items by viewModel.searchResults.observeAsState(listOf())
    LaunchedEffect(Unit) {

        viewModel.findAccount("%${query}%")


    }
    Scaffold(
        backgroundColor = backGroundColor,
        topBar = {
            TopAppBar(
                title = { Text("Search Result") },

                Modifier.background(Color.Transparent),

                backgroundColor = Color.Transparent ,
                elevation = 0.0.dp ,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )


                    }
                }
            )


        } ,


content = {
Column(

    modifier = Modifier.fillMaxSize() ,



) {
    Spacer(modifier = Modifier.height(20.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        elevation = 10.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        if (items.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally ,
                    verticalArrangement = Arrangement.Center
                ){

                    Image(painter = painterResource(id = com.kururu.password_manager.R.drawable.noresults),

                        contentDescription ="" )
                    Text(text = "No result found")
                }
            }
        }
        else {

                Column(
                    modifier = Modifier.padding(15.dp)
                ) {


                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(count = items.size, itemContent = { item ->


                            PasswordContainer(
                                navController,
                                items[item],
                                onTryShowDetails
                            )


                        })

                }
            }
        }
    }

}
}
        )
}