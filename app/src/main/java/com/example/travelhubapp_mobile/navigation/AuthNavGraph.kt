package com.example.travelhubapp_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import com.example.travelhubapp_mobile.ui.screens.*

@Composable
fun AuthNavGraph() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val hotelViewModel: HotelViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HotelViewModel(TokenManager(context)) as T
            }
        },
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
                onBookingClick = { id -> navController.navigate("${Routes.RESERVA_PRINT}/$id") },
                onPagar = { id ->
                    val booking = hotelViewModel.myBookings.find { it.id == id }
                    hotelViewModel.selectedBookingForPayment = booking
                    navController.navigate(Routes.PAGO)
                },
                onLogout = logout,
                viewModel = hotelViewModel
            )
        }
        composable(
            route = "${Routes.RESERVA_PRINT}/{bookingId}",
            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString("bookingId") ?: ""
            ReservaPrintScreen(
                bookingId = bookingId,
                onBack = { navController.popBackStack() },
                onHome = { navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } } },
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
                onBack = {
                    hotelViewModel.error = null
                    navController.popBackStack()
                },
                onHotelClick = { navController.navigate(Routes.DETALLE_HABITACION) },
                viewModel = hotelViewModel
            )
        }
        composable(Routes.DETALLE_HABITACION) {
            HabitacionDetalleScreen(
                onBack = { navController.popBackStack() },
                onReservar = { navController.navigate(Routes.RESERVA) },
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
        composable(Routes.PAGO) {
            PagoScreen(
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Routes.MIS_RESERVAS) {
                        popUpTo(Routes.MIS_RESERVAS) { inclusive = true }
                    }
                    hotelViewModel.fetchBookings()
                },
                viewModel = hotelViewModel
            )
        }
    }
}
