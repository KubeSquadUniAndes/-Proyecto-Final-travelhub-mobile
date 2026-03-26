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
        // CA1: Pantalla de bienvenida con opciones
        composable(Routes.BIENVENIDA) {
            BienvenidaScreen(
                // CA2: Redirige a vista de inicio de sesión
                onLogin = { navController.navigate(Routes.LOGIN) },
                // CA4: Redirige a vista de registro como viajero
                onRegistro = { navController.navigate(Routes.REGISTRO) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLogin = { /* TODO: autenticar y navegar a Home */ },
                onRegister = { navController.navigate(Routes.REGISTRO) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.REGISTRO) {
            RegistroScreen(
                onRegister = { /* TODO: crear cuenta y navegar a Home */ },
                onLogin = { navController.navigate(Routes.LOGIN) { popUpTo(Routes.REGISTRO) { inclusive = true } } },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
