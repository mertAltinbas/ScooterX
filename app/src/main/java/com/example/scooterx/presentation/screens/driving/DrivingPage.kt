package com.example.scooterx.presentation.screens.driving

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scooterx.R
import com.example.scooterx.domain.entities.Scooter
import com.example.scooterx.presentation.ui.theme.ScooterXTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import java.lang.Math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class DrivingPage : ComponentActivity() {

    private val data: Scooter by lazy {
        intent?.getSerializableExtra(UrunAdi) as Scooter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScooterXTheme {
                BackHandler(true){}
                val systemUiController = rememberSystemUiController()
                val isDark = isSystemInDarkTheme()
                val navigationBackground = Color.Black
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = navigationBackground, darkIcons = isDark
                    )
                    systemUiController.setStatusBarColor(
                        color = navigationBackground, darkIcons = isDark
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.scooter_x),
                            contentDescription = null, modifier = Modifier.fillMaxSize()
                        )
                        Timer(
                            totalTime = 100,
                            handleColor = Color.Green,
                            inactiveBarColor = Color.Gray,
                            activeBarColor = Color(0xFF37B900),
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Timer(
        totalTime: Int,
        handleColor: Color,
        inactiveBarColor: Color,
        activeBarColor: Color,
        modifier: Modifier = Modifier,
        initialValue: Float = 1f,
        strokeWidth: Dp = 5.dp
    ) {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        var value by remember {
            mutableStateOf(initialValue)
        }
        var currentTime by remember {
            mutableStateOf(1)
        }
        var isTimerRunning by remember {
            mutableStateOf(true)
        }
        val price = data.fee.toFloat() / 60
        val deneme2 = currentTime * price
        val result = (deneme2 * 100.0).roundToInt() / 100.0

        LaunchedEffect(
            key1 = currentTime,
            key2 = isTimerRunning
        )
        {
            if (currentTime >= 0 && isTimerRunning) {
                delay(1000)
                currentTime += 1
                value = currentTime / totalTime.toFloat()
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .onSizeChanged { size = it }
        ) {
            Canvas(modifier = modifier) {
                drawArc(
                    color = inactiveBarColor,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = activeBarColor,
                    startAngle = -215f,
                    sweepAngle = 250f * value,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                val center = Offset(size.height / 2f, size.width / 2f)
                val beta = (250f * value + 145f) * (PI / 180f).toFloat()
                val r = size.width / 2f
                val a = cos(beta) * r
                val b = sin(beta) * r
                drawPoints(
                    listOf(Offset(center.x + a, center.y + b)),
                    pointMode = PointMode.Points,
                    color = handleColor,
                    strokeWidth = (strokeWidth * 4f).toPx(),
                    cap = StrokeCap.Round
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = currentTime.toString(),
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { isTimerRunning = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text("End Ride", fontSize = 15.sp, color = Color.White)
                }
                Text(
                    text = "Total Price: $result$",
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    companion object {
        private const val UrunAdi = "urunid"
        fun newIntent(context: Context, data: Scooter) =
            Intent(context, DrivingPage::class.java).apply {
                putExtra(UrunAdi, data)
            }
    }
}