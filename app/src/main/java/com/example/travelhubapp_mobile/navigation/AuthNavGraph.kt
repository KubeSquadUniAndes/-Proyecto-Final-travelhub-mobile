package com.example.travelhubapp_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import com.example.travelhubapp_mobile.ui.screens.*

@Composable
fun AuthNavGraph() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val hotelViewModel: HotelViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HotelViewModel(TokenManager(context)) as T
            }
        }
    )
    val logout: () -> Unit = {
        navController.navigate(Routes.BIENVENIDA) { popUpTo(0) { inclusive = true } }
    }

    NavHost(navController = navController, startDestination = Routes.BIENVENIDA) {
        composable(Routes.BIENVENIDA) {
            BienvenidaScreen(
                onLogin = { navController.navigate(Routes.LOGIN) },
                onRegistro = { navController.navigate(Routes.REGISTRO) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onLogin = { navController.navigate(Routes.HOME) { popUpTo(Routes.BIENVENIDA) { inclusive = true } } },
                onRegister = { navController.navigate(Routes.REGISTRO) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.REGISTRO) {
            RegistroScreen(
                onRegister = { navController.navigate(Routes.LOGIN) { popUpTo(Routes.REGISTRO) { inclusive = true } } },
                onLogin = { navController.navigate(Routes.LOGIN) { popUpTo(Routes.REGISTRO) { inclusive = true } } },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                onReservar = { navController.navigate(Routes.BUSCAR_HOTELES) },
                onPerfil = { navController.navigate(Routes.PERFIL) },
                onMisReservas = { navController.navigate(Routes.MIS_RESERVAS) },
                onLogout = logout,
                viewModel = hotelViewModel
            )
        }
        composable(Routes.MIS_RESERVAS) {
            MisReservasScreen(
                onHome = { navController.navigate(Routes.HOME) },
                onPerfil = { navController.navigate(Routes.PERFIL) },
                onBuscarMas = { navController.navigate(Routes.HOME) },
                onLogout = logout,
                viewModel = hotelViewModel
            )
        }
        composable(Routes.PERFIL) {
            PerfilScreen(
                onHome = { navController.navigate(Routes.HOME) },
                onMisReservas = { navController.navigate(Routes.MIS_RESERVAS) },
                onLogout = logout
            )
        }
        composable(Routes.BUSCAR_HOTELES) {
            BuscarHotelesScreen(
                onBack = { navController.popBackStack() },
                onHotelClick = { navController.navigate(Routes.RESERVA) },
                viewModel = hotelViewModel
            )
        }
        composable(Routes.RESERVA) {
            ReservaScreen(
                onBack = { navController.popBackStack() },
                onSuccess = { navController.navigate(Routes.CONFIRMACION) },
                viewModel = hotelViewModel
            )
        }
        composable(Routes.CONFIRMACION) {
            ConfirmacionReservaScreen(
                onHome = { navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } } },
                viewModel = hotelViewModel
            )
        }
    }
}
