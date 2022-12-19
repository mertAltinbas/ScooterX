package com.example.scooterx
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scooterx.presentation.screens.instruction.view.InstructionPage
import com.example.scooterx.presentation.screens.login_register.view.LoginScreen
import com.example.scooterx.presentation.screens.login_register.view.SignUpScreen
import com.example.scooterx.presentation.screens.login_register.view_model.LoginViewModel


enum class LoginRoutes {
    Signup,
    SignIn
}
enum class HomeRoutes {
    Home,
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = LoginRoutes.SignIn.name
    ) {
        composable(route = LoginRoutes.SignIn.name) {
            LoginScreen(onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name) {
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.Signup.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name) {
                    popUpTo(LoginRoutes.Signup.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
        composable(route = HomeRoutes.Home.name) {
            InstructionPage()
        }
    }
}