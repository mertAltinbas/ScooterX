package com.example.scooterx.presentation.screens.login_register.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.scooterx.R
import com.example.scooterx.presentation.screens.login_register.view_model.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.walpaper2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(3.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(
                    CutCornerShape(
                        topStart = 8.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colors.surface
                )

                if (isError) {
                    Text(
                        text = loginUiState?.loginError ?: "Unknown Error",
                        color = Color.Red,
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.userName ?: "",
                    textStyle = TextStyle(Color.White),
                    onValueChange = { loginViewModel?.onUserNameChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colors.secondary,
                        unfocusedBorderColor = colors.secondary,
                        focusedLabelColor = colors.surface,
                        cursorColor = colors.primaryVariant
                    ),
                    label = {
                        Text(text = "Email", color = Color.White)
                    },
                    isError = isError,
                )


                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = (loginUiState?.password ?: ""),
                    onValueChange = { loginViewModel?.onPasswordNameChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colors.secondary,
                        unfocusedBorderColor = colors.secondary,
                        focusedLabelColor = colors.surface,
                        cursorColor = colors.primaryVariant
                    ),
                    textStyle = TextStyle(Color.White),
                    label = {
                        Text(text = "Password", color = Color.White)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = isError
                )

                Button(onClick = { loginViewModel?.loginUser(context) },
                    colors = ButtonDefaults.buttonColors(Color.Cyan)) {
                    Text(text = "SIGN IN")
                }
                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don't have an Account?", color = Color.White)
                    Spacer(modifier = Modifier.size(8.dp))
                    TextButton(onClick = { onNavToSignUpPage.invoke() }) {
                        Text(text = "SignUp", color = Color.Cyan)
                    }
                }
                if (loginUiState?.isLoading == true) {
                    CircularProgressIndicator()
                }

                LaunchedEffect(key1 = loginViewModel?.hasUser) {
                    if (loginViewModel?.hasUser == true) {
                        onNavToHomePage.invoke()
                    }
                }
            }
        }
    }
}

