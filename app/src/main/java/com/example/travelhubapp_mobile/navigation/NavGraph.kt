package com.example.travelhubapp_mobile.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.travelhubapp_mobile.ui.components.THBottomBar
import com.example.travelhubapp_mobile.ui.screens.*

@Composable
fun TravelHubNavGraph() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val tabRoutes = listOf(Routes.HOME, Routes.BUSCAR_HOTELES, Routes.MIS_RESERVAS, Routes.PERFIL)
    val showBottomBar = currentRoute in tabRoutes
    val selectedTab = tabRoutes.indexOf(currentRoute)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                THBottomBar(selected = selectedTab) { index ->
                    navController.navigate(tabRoutes[index]) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = Routes.LOGIN, Modifier.padding(padding)) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLogin = { navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                    onRegister = { navController.navigate(Routes.REGISTRO) },
                    onBack = {}
                )
            }
            composable(Routes.REGISTRO) {
                RegistroScreen(
                    onRegister = { navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                    onLogin = { navController.popBackStack() },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.HOME) {
                HomeScreen(onSearch = { navController.navigate(Routes.BUSCAR_HOTELES) })
            }
            composable(Routes.BUSCAR_HOTELES) {
                BuscarHotelesScreen(
                    onBack = { navController.popBackStack() },
                    onHotelClick = { navController.navigate(Routes.CHECKOUT) }
                )
            }
            composable(Routes.CHECKOUT) {
                CheckoutScreen(
                    onBack = { navController.popBackStack() },
                    onConfirm = { navController.navigate(Routes.RESERVA_CONFIRMADA) }
                )
            }
            composable(Routes.RESERVA_CONFIRMADA) {
                ReservaConfirmadaScreen(
                    onHome = { navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } } },
                    onPrint = { navController.navigate(Routes.IMPRIMIR) }
                )
            }
            composable(Routes.MIS_RESERVAS) {
                MisReservasScreen(onSearch = { navController.navigate(Routes.BUSCAR_HOTELES) })
            }
            composable(Routes.PERFIL) {
                PerfilScreen(onLogout = { navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } } })
            }
            composable(Routes.IMPRIMIR) {
                ImprimirScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
