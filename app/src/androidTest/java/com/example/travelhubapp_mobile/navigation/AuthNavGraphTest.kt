package com.example.travelhubapp_mobile.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test

class AuthNavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun startsOnBienvenidaScreen() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarme como viajero").assertIsDisplayed()
    }

    @Test
    fun navigatesToLoginScreen() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.onNodeWithText("Bienvenido de nuevo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
    }

    @Test
    fun navigatesToRegistroScreen() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.onAllNodesWithText("Crear cuenta").onFirst().assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre *").assertIsDisplayed()
    }

    @Test
    fun navigatesFromLoginToRegistro() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.onNodeWithText("Crear cuenta nueva")
            .performScrollTo().performClick()
        composeTestRule.onAllNodesWithText("Crear cuenta").onFirst().assertIsDisplayed()
    }

    @Test
    fun navigatesFromRegistroToLogin() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.onNodeWithText("Inicia sesión")
            .performScrollTo().performClick()
        composeTestRule.onNodeWithText("Bienvenido de nuevo").assertIsDisplayed()
    }

    @Test
    fun backFromLogin_returnsToBienvenida() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Registrarme como viajero").assertIsDisplayed()
    }

    @Test
    fun backFromRegistro_returnsToBienvenida() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }
}
