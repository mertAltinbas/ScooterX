package com.example.scooterx.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarX(icon: ImageVector, onTap: () -> Unit = {}) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp)
            .fillMaxWidth(),
        backgroundColor = Color.Transparent,
        title = {},
        navigationIcon = {
            Button(
                colors = ButtonDefaults.buttonColors(colors.primary),
                elevation = ButtonDefaults.elevation(0.dp),
                modifier= Modifier
                    .width(50.dp)
                    .height(50.dp),
                onClick = onTap
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Back",
                    tint = colors.surface,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
    )
}