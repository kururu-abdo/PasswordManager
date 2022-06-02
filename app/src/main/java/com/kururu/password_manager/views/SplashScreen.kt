package com.kururu.password_manager.views

import android.app.Application
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.kururu.password_manager.Events.AppEvents
import com.kururu.password_manager.R
import com.kururu.password_manager.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController ,



                 viewModel: MainViewModel =
                     MainViewModel(LocalContext.current.applicationContext as Application)
                 ) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000L)
if (viewModel.isFirstTime.value == true){
  viewModel.onEvent(AppEvents.FirstTimeEvent() ,navController)
}else {
    navController.navigate("/")
}

    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.saver),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))
    }
}
