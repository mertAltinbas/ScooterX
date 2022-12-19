package com.example.scooterx.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonX(
    imageVector: ImageVector,
    backgroundColor: Color? = null,
    onTap: () -> Unit = {},
    tint:Color,
    text : String,
    textColor:Color
) {
    TextButton(
        modifier = Modifier
            .clip(CircleShape)
            .width(110.dp)
            .height(45.dp)
            .background(backgroundColor ?: colors.onSurface),
        onClick = onTap,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = text, color = textColor)
        }
    }
}