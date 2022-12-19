package com.example.scooterx.presentation.screens.user_profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scooterx.R
import com.example.scooterx.domain.entities.LoginUiState
import com.example.scooterx.presentation.components.TopAppBarX
import com.example.scooterx.presentation.ui.theme.ScooterXTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Suppress("SameParameterValue")
class UserProfile : ComponentActivity() {
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
                Scaffold(
                    topBar = {
                        TopAppBarX(icon = Icons.Default.ArrowBackIos, onTap = {
                            onBackPressedDispatcher.onBackPressed()
                        })
                    }) {
                    Box(
                        Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        PageView(LoginUiState())
                    }
                }
            }
        }
    }

    @Composable
    private fun PageView(user:LoginUiState) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val state = rememberScrollState()
        Column(
            Modifier
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .verticalScroll(state)
        ) {
            Surface(
                modifier = Modifier
                    .width(screenWidth)
                    .height(150.dp),
                color = colors.secondary,
                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Column {
                            Text(
                                text = "Hello :)",
                                fontSize = 25.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Scooter Wallet",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column {
                            Text(
                                text = "BALANCE",
                                fontSize = 15.sp,
                                color = Color.White,
                            )
                            Text(
                                text = "$0.00",
                                fontSize = 25.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.scootericon),
                            contentDescription = null,
                            modifier = Modifier.size(75.dp)
                        )
                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.clickable { }
                        ) {
                            Text(
                                text = "ADD BALANCE",
                                color = colors.primary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                            )
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 15.dp)
            ) {
                ColumnPage(icon = Icons.Default.ContactPhone, text = "Contact Us")
                ColumnPage(icon = Icons.Default.Notifications, text = "Notifications")
                ColumnPage(icon = Icons.Default.Wallet, text = "Scooter Wallet")
                ColumnPage(icon = Icons.Default.NotificationImportant, text = "Promos")
                ColumnPage(icon = Icons.Default.PresentToAll, text = "Invite Friend")
                ColumnPage(icon = Icons.Default.PunchClock, text = "Ride History")
                ColumnPage(icon = Icons.Default.StickyNote2, text = "Ride Guidelines")
                ColumnPage(icon = Icons.Default.QuestionMark, text = "FAQ")
                ColumnPage(icon = Icons.Default.Settings, text = "Settings")
            }
        }
    }

    @Composable
    private fun ColumnPage(
        text: String,
        icon: ImageVector,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp))
                .clickable { }
        ) {
            Row(
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = text,
                    fontSize = 18.sp,
                )
            }
        }
    }
}