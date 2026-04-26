package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class PerfilScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysProfileHeader() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(onLogout = {}, onHome = {}, onMisReservas = {})
            }
        }

        composeTestRule.onNodeWithText("Mi Perfil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Viajero TravelHub").assertIsDisplayed()
        composeTestRule.onNodeWithText("8").assertIsDisplayed() // StatCard value
        composeTestRule.onNodeWithText("Reservas").assertIsDisplayed() // StatCard label
    }

    @Test
    fun displaysRecentReservations() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(onLogout = {}, onHome = {}, onMisReservas = {})
            }
        }

        composeTestRule.onNodeWithText("Reservas recientes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hotel Grand Luxury").assertIsDisplayed()
        composeTestRule.onNodeWithText("Beachfront Paradise Resort").assertIsDisplayed()
    }

    @Test
    fun displaysAccountMenu() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(onLogout = {}, onHome = {}, onMisReservas = {})
            }
        }

        composeTestRule.onNodeWithText("Mi cuenta").assertIsDisplayed()
        composeTestRule.onNodeWithText("Información personal").assertIsDisplayed()
        composeTestRule.onNodeWithText("Métodos de pago").assertIsDisplayed()
        composeTestRule.onNodeWithText("Notificaciones").assertIsDisplayed()
        composeTestRule.onNodeWithText("Seguridad").assertIsDisplayed()
    }

    @Test
    fun logoutButton_triggersCallback() {
        var logoutClicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(
                    onLogout = { logoutClicked = true },
                    onHome = {},
                    onMisReservas = {}
                )
            }
        }

        // Scroll to the bottom to find the button if needed, but PerfilScreen is small enough usually
        // or we can use performScrollTo() if it was in a scrollable list
        composeTestRule.onNodeWithText("Cerrar sesión").performClick()
        assert(logoutClicked)
    }

    @Test
    fun bottomBar_navigation() {
        var homeClicked = false
        var misReservasClicked = false

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(
                    onLogout = {},
                    onHome = { homeClicked = true },
                    onMisReservas = { misReservasClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Inicio").performClick()
        assert(homeClicked)

        composeTestRule.onNodeWithText("Reservas").performClick()
        assert(misReservasClicked)
    }
}
