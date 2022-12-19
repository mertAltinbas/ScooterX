package com.example.dnm.presentation.screens.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Dangerous
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scooterx.R
import com.example.scooterx.domain.entities.Scooter
import com.example.scooterx.presentation.components.ButtonX
import com.example.scooterx.presentation.components.TopAppBarX
import com.example.scooterx.presentation.components.bitmapDescriptorFromVector
import com.example.scooterx.presentation.screens.driving.DrivingPage
import com.example.scooterx.presentation.ui.theme.ScooterXTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Suppress("DEPRECATION")
class DetailPage : ComponentActivity() {
    private val data: Scooter by lazy {
        intent?.getSerializableExtra(UrunAdi) as Scooter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScooterXTheme {
                val systemUiController = rememberSystemUiController()
                val isDark = isSystemInDarkTheme()
                val navigationBackground = colors.surface
                systemUiController.isStatusBarVisible = false
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = navigationBackground, darkIcons = !isDark
                    )
                }

                Scaffold(backgroundColor = colors.surface, bottomBar = { BottomNavigationX() }) {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .background(colors.surface)
                    ) {
                        MapContent()
                    }
                }
            }
        }
    }

    @Composable
    private fun MapContent() {
        val scrollState = rememberScrollState()
        Box {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Box {
                    SpecimenMap()
                    Column {
                        Spacer(
                            modifier = Modifier.aspectRatio(1.2f)
                        )
                        Surface(
                            shape = RoundedCornerShape(30.dp, 30.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box {
                                Column(
                                    Modifier
                                        .background(colors.surface)
                                        .fillMaxSize()
                                        .padding(vertical = 15.dp, horizontal = 25.dp)
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp),
                                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = data.name, style = TextStyle(
                                                fontSize = 30.sp, fontWeight = FontWeight.SemiBold
                                            ), color = colors.primary
                                        )
                                        Surface(color = colors.primary, shape = CircleShape) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(7.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Timelapse,
                                                    contentDescription = null,
                                                    tint = colors.surface
                                                )
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(text = "30:00", color = colors.surface)
                                            }
                                        }
                                    }
                                    Text(
                                        text = data.plaka,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))

                                    Surface(color = colors.primary, shape = CircleShape) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.BatteryChargingFull,
                                                contentDescription = null,
                                                tint = colors.secondary
                                            )
                                            Text(text = data.battery + "km Range")
                                        }
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(35.dp),
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    ) {
                                        Column {
                                            Text(
                                                text = stringResource(id = R.string.speed),
                                                color = Color.Gray,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(
                                                text = data.speed + "km/h",
                                                color = colors.primary,
                                                fontSize = 25.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Column {
                                            Text(
                                                text = stringResource(id = R.string.battery),
                                                color = Color.Gray,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(
                                                text = data.battery + "%",
                                                color = colors.primary,
                                                fontSize = 25.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Column {
                                        Text(
                                            text = stringResource(id = R.string.fee),
                                            color = Color.Gray,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = "$ " + data.fee + "/min",
                                            color = colors.primary,
                                            fontSize = 25.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 10.dp, start = 188.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.scooter_icon),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.size(300.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            TopAppBarX(
                icon = Icons.Default.ArrowBackIos,
                onTap = { onBackPressedDispatcher.onBackPressed()
                },
            )
        }
    }

    @Composable
    private fun SpecimenMap() {
        val context = LocalContext.current
        val location = LatLng(
            data.latitude.toDouble(), data.longitude.toDouble()
        )
        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 17f)
        }
        GoogleMap(
            modifier = Modifier.size(500.dp), cameraPositionState = cameraPosition
        ) {
            Marker(
                state = MarkerState(location),
                title = data.plaka,
                icon = bitmapDescriptorFromVector(
                    context,
                    R.drawable.scootericon,
                    R.color.black,
                )
            )
        }
    }

    @Composable
    private fun BottomNavigationX() {
        val context = LocalContext.current
        BottomNavigation(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier
                .height(110.dp)
                .padding(horizontal = 25.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonX(
                        imageVector = Icons.Outlined.Dangerous,
                        tint = Color.Red,
                        text = stringResource(id = R.string.cancel),
                        textColor = colors.primary,
                        onTap = { onBackPressedDispatcher.onBackPressed() })
                    ButtonX(
                        imageVector = Icons.Default.Notifications,
                        text = stringResource(id = R.string.ring),
                        textColor = colors.primary,
                        tint = colors.primary
                    )
                    ButtonX(
                        imageVector = Icons.Default.Report,
                        text = stringResource(id = R.string.report),
                        textColor = colors.primary,
                        tint = colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                TextButton(
                    onClick = {
                        context.startActivity(DrivingPage.newIntent(context, data))
                    },
                    colors = ButtonDefaults.buttonColors(colors.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.scan_to_ride),
                            color = colors.primary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val UrunAdi = "urunid"
        fun newIntent(context: Context, data: Scooter) =
            Intent(context, DetailPage::class.java).apply {
                putExtra(UrunAdi, data)
            }

    }
}