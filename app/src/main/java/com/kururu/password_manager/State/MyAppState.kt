package com.kururu.password_manager.State

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MyAppState(

    val navController: NavHostController,
    private val resources: Resources,
    /* ... */
) {


    // Navigation logic, which is a type of UI logic
    fun navigateToBottomBarRoute(route: String) { /* ... */ }

    // Show snackbar using Resources
    fun showSnackbar(message: String) { /* ... */ }
}


@Composable
fun rememberMyAppState(

    navController: NavHostController = rememberNavController(),
    resources: Resources = LocalContext.current.resources,
    /* ... */
) = remember( navController, resources, /* ... */) {
    MyAppState(navController, resources)
}