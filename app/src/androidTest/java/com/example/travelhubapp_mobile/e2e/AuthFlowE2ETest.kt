package com.example.travelhubapp_mobile.e2e

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.navigation.AuthNavGraph
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test

class AuthFlowE2ETest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchApp() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { AuthNavGraph() }
        }
    }

    @Test
    fun fullFlow_bienvenida_displaysCorrectly() {
        launchApp()
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarme como viajero").assertIsDisplayed()
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
    }

    @Test
    fun fullFlow_loginValidation_showsErrorOnEmptyFields() {
        launchApp()
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Iniciar sesión")
            .performScrollTo().performClick()
        composeTestRule.onNodeWithText("Completa todos los campos")
            .assertIsDisplayed()
    }

    @Test
    fun fullFlow_loginValidation_showsErrorOnEmptyPassword() {
        launchApp()
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodes(hasSetTextAction())[0]
            .performTextInput("test@mail.com")
        composeTestRule.onNodeWithText("Iniciar sesión")
            .performScrollTo().performClick()
        composeTestRule.onNodeWithText("Completa todos los campos")
            .assertIsDisplayed()
    }

    @Test
    fun fullFlow_navigateToRegistro_displaysAllFields() {
        launchApp()
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Información personal").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre *").assertIsDisplayed()
        composeTestRule.onNodeWithText("Apellido *").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email *").assertIsDisplayed()
        composeTestRule.onNodeWithText("Teléfono *").assertIsDisplayed()
    }

    @Test
    fun fullFlow_registroValidation_showsErrorOnEmptyFields() {
        launchApp()
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Crear cuenta")[1]
            .performScrollTo().performClick()
        composeTestRule.onNodeWithText("Completa todos los campos")
            .assertIsDisplayed()
    }

    @Test
    fun fullFlow_registroToLogin_navigation() {
        launchApp()
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Inicia sesión")
            .performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Bienvenido de nuevo").assertIsDisplayed()
    }

    @Test
    fun fullFlow_loginToRegistro_navigation() {
        launchApp()
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Crear cuenta nueva")
            .performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Crear cuenta").onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun fullFlow_backNavigation_loginToBienvenida() {
        launchApp()
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Registrarme como viajero").assertIsDisplayed()
    }

    @Test
    fun fullFlow_backNavigation_registroToBienvenida() {
        launchApp()
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }

    @Test
    fun fullFlow_registroForm_acceptsInput() {
        launchApp()
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.waitForIdle()

        val fields = composeTestRule.onAllNodes(hasSetTextAction())
        fields[0].performTextInput("Daniel")
        fields[1].performTextInput("Zapata")
        fields[2].performTextInput("daniel@test.com")
        fields[3].performTextInput("+57 302 228 8110")

        composeTestRule.onNodeWithText("Daniel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Zapata").assertIsDisplayed()
        composeTestRule.onNodeWithText("daniel@test.com").assertIsDisplayed()
    }

    @Test
    fun fullFlow_loginForm_acceptsInput() {
        launchApp()
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
        composeTestRule.waitForIdle()

        val fields = composeTestRule.onAllNodes(hasSetTextAction())
        fields[0].performTextInput("daniel@test.com")
        fields[1].performTextInput("MyPassword123!")

        composeTestRule.onNodeWithText("daniel@test.com").assertIsDisplayed()
    }

    @Test
    fun fullFlow_registroDefaultIdType_isDisplayed() {
        launchApp()
        composeTestRule.onNodeWithText("Registrarme como viajero").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Cédula (CC)")
            .performScrollTo().assertIsDisplayed()
    }
}
