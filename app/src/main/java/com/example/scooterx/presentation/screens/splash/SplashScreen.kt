package com.example.scooterx.presentation.screens.splash
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scooterx.R
import kotlinx.coroutines.delay


private const val SplashWaitTime: Long = 2000

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        LaunchedEffect(true) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(painterResource(id = R.drawable.section), contentDescription = null)
            Spacer(modifier = modifier.height(15.dp))
            Text(text = "ScooterX", fontSize = 50.sp, color = Color.White)
        }
    }
}
