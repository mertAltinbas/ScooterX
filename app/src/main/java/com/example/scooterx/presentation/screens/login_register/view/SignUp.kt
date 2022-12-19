package com.example.scooterx.presentation.screens.login_register.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
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
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.walpaper),
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
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colors.surface
                )

                if (isError) {
                    Text(
                        text = loginUiState?.signUpError ?: "unknown error",
                        color = Color.Red,
                    )
                }

                // Email Section
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.userNameSignUp ?: "",
                    onValueChange = { loginViewModel?.onUserNameChangeSignup(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    textStyle = TextStyle(Color.White),
                    label = {
                        Text(text = "Email", color = Color.White)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.primaryVariant
                    ),
                    isError = isError
                )

                // Password
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.passwordSignUp ?: "",
                    onValueChange = { loginViewModel?.onPasswordChangeSignup(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    textStyle = TextStyle(Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.primaryVariant
                    ),
                    label = {
                        Text(text = "Password", color = Color.White)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = isError
                )

                // Password Check
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = loginUiState?.confirmPasswordSignUp ?: "",
                    onValueChange = { loginViewModel?.onConfirmPasswordChange(it) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    label = {
                        Text(text = "Confirm Password", color = Color.White)
                    },
                    textStyle = TextStyle(Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary,
                        unfocusedBorderColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.primaryVariant
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = isError
                )

                Button(
                    onClick = { loginViewModel?.createUser(context) },
                    colors = ButtonDefaults.buttonColors(
                        Color.Cyan
                    )
                ) {
                    Text(text = "SIGN UP")
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Already have an Account?", color = Color.White)
                    Spacer(modifier = Modifier.size(8.dp))
                    TextButton(onClick = { onNavToLoginPage.invoke() }) {
                        Text(text = "Sign In", color = Color.Cyan)
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