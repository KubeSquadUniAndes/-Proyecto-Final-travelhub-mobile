package com.example.travelhubapp_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelhubapp_mobile.ui.screens.*

@Composable
fun AuthNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.BIENVENIDA) {
        composable(Routes.BIENVENIDA) {
            BienvenidaScreen(
                onLogin = { navController.navigate(Routes.LOGIN) },
                onRegistro = { navController.navigate(Routes.REGISTRO) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLogin = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.BIENVENIDA) { inclusive = true }
                    }
                },
                onRegister = {
                    navController.navigate(Routes.REGISTRO) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.REGISTRO) {
            RegistroScreen(
                onRegister = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTRO) { inclusive = true }
                    }
                },
                onLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTRO) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Routes.BIENVENIDA) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
