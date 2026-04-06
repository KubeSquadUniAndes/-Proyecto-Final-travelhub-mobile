package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test

class BienvenidaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysAppName() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = {}, onRegistro = {}) }
        }
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
    }

    @Test
    fun displaysLogo() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = {}, onRegistro = {}) }
        }
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
    }

    @Test
    fun displaysSubtitle() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = {}, onRegistro = {}) }
        }
        composeTestRule.onNodeWithText("Descubre los mejores hospedajes\npara tu próximo viaje").assertIsDisplayed()
    }

    @Test
    fun displaysLoginButton() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = {}, onRegistro = {}) }
        }
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }

    @Test
    fun displaysRegisterButton() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = {}, onRegistro = {}) }
        }
        composeTestRule.onNodeWithText("Registrarme como viajero").assertIsDisplayed()
    }

    @Test
    fun displaysTermsText() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = {}, onRegistro = {}) }
        }
        composeTestRule.onNodeWithText("Al continuar, aceptas nuestros Términos de servicio y Política de privacidad")
            .assertIsDisplayed()
    }

    @Test
    fun loginButtonClick_callsCallback() {
        var clicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = { clicked = true }, onRegistro = {}) }
        }
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        assert(clicked)
    }

    @Test
    fun registerButtonClick_callsCallback() {
        var clicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme { BienvenidaScreen(onLogin = {}, onRegistro = { clicked = true }) }
        }
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        assert(clicked)
    }
}
