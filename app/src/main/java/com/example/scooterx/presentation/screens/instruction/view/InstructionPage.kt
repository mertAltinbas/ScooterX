package com.example.scooterx.presentation.screens.instruction.view

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.scooterx.core.OnError
import com.example.scooterx.core.OnSuccess
import com.example.scooterx.domain.entities.Instruction
import com.example.scooterx.presentation.screens.map.view.MapScreenActivity
import com.example.scooterx.presentation.screens.map.view_model.InstructionRepo
import com.example.scooterx.presentation.screens.map.view_model.InstructionViewModel
import com.example.scooterx.presentation.screens.map.view_model.InstructionViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun InstructionPage(
    instructionsViewModel: InstructionViewModel = viewModel(
        factory = InstructionViewModelFactory(
            InstructionRepo()
        )
    )
) {
    when (val instructionList =
        instructionsViewModel.instructionStateFlow.asStateFlow().collectAsState().value) {
        is OnError -> {
            Text(text = "Please try again")
        }

        is OnSuccess -> {
            val systemUiController = rememberSystemUiController()
            systemUiController.isStatusBarVisible = false
            val listOfInstruction =
                instructionList.querySnapshot?.toObjects(Instruction::class.java)
            listOfInstruction?.let {
                ShowLazyList(data = it)
            }
        }
        else -> {}
    }
}

@Composable
fun ShowLazyList(data: MutableList<Instruction>) {
    LazyColumn(userScrollEnabled = false) {
        items(data) {
            CardItem(it)
        }
    }
}

@Composable
fun CardItem(data: Instruction) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Box {
        AsyncImage(
            model = data.image,
            contentDescription = null,
            modifier = Modifier.aspectRatio(0.49f),
            contentScale = ContentScale.Crop,
        )
        Column(
            Modifier
                .height(screenHeight)
                .padding(bottom = 25.dp, start = 10.dp, end = 10.dp, top = 35.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = data.slogan,
                fontSize = 36.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Column() {
                TextButton(
                    onClick = {
                        context.startActivity(Intent(context, MapScreenActivity::class.java))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Let's go!",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                    )
                }

                Text(
                    text = data.privacy,
                    fontSize = 15.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}