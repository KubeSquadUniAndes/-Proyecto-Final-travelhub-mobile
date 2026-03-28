package com.example.travelhubapp_mobile.navigation

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
class AuthNavGraphRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun startsOnBienvenidaScreen() {
        composeTestRule.setContent { TravelHubAppMobileTheme { AuthNavGraph() } }
        composeTestRule.onNodeWithText("TravelHub").assertExists()
        composeTestRule.onNodeWithText("Iniciar sesión").assertExists()
        composeTestRule.onNodeWithText("Registrarme como viajero").assertExists()
    }

    @Test
    fun navigatesToLoginScreen() {
        composeTestRule.setContent { TravelHubAppMobileTheme { AuthNavGraph() } }
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.onNodeWithText("Bienvenido de nuevo").assertExists()
    }

    @Test
    fun navigatesToRegistroScreen() {
        composeTestRule.setContent { TravelHubAppMobileTheme { AuthNavGraph() } }
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.onNodeWithText("Nombre completo").assertExists()
    }

    @Test
    fun navigatesFromLoginToRegistro() {
        composeTestRule.setContent { TravelHubAppMobileTheme { AuthNavGraph() } }
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Crear cuenta nueva").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Crear cuenta").onFirst().assertExists()
    }

    @Test
    fun navigatesFromRegistroToLogin() {
        composeTestRule.setContent { TravelHubAppMobileTheme { AuthNavGraph() } }
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Inicia sesión").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Bienvenido de nuevo").assertExists()
    }

    @Test
    fun backFromLogin_returnsToBienvenida() {
        composeTestRule.setContent { TravelHubAppMobileTheme { AuthNavGraph() } }
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Registrarme como viajero").assertExists()
    }

    @Test
    fun backFromRegistro_returnsToBienvenida() {
        composeTestRule.setContent { TravelHubAppMobileTheme { AuthNavGraph() } }
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Iniciar sesión").assertExists()
    }
}
