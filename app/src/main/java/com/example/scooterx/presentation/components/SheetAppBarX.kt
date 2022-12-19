package com.example.scooterx.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetAppBarX(onTap: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet() }
    )
    {
        TopAppBar(
            elevation = 0.dp,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            backgroundColor = Color.Transparent,
            title = {},
            navigationIcon = {
                Button(
                    colors = ButtonDefaults.buttonColors(colors.primary),
                    modifier= Modifier
                        .width(50.dp)
                        .height(50.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = onTap
                ) {
                    Icon(
                        imageVector = Icons.Default.TableRows,
                        contentDescription = "Table",
                        tint = colors.surface,
                        modifier = Modifier.size(35.dp)
                    )
                }
            },
            actions = {
                Button(
                    colors = ButtonDefaults.buttonColors(colors.primary),
                    modifier= Modifier
                        .width(50.dp)
                        .height(50.dp),
                    elevation =ButtonDefaults.elevation(0.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (sheetState.isVisible)sheetState.hide()
                            else sheetState.show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ElectricScooter,
                        contentDescription = "Back",
                        tint = colors.surface,
                        modifier = Modifier.size(35.dp)
                    )
                }
            },
        )
    }
}
@Composable
fun BottomSheet() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface)
            .clip(RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp))
    ) {
        Column(modifier = Modifier.padding(45.dp),Arrangement.Center,Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ElectricScooter,
                contentDescription = "Back",
                tint = colors.primary,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                textAlign = TextAlign.Center,
                text = "Would you like to see more ScooterX in your area?",
                style = MaterialTheme.typography.body1
            )
        }
    }
}






