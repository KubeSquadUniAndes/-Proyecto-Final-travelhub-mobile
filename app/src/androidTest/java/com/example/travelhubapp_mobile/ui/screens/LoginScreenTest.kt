package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setScreen(
        onLogin: () -> Unit = {},
        onRegister: () -> Unit = {},
        onBack: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { LoginScreen(onLogin, onRegister, onBack) }
        }
    }

    @Test
    fun displaysAppName() {
        setScreen()
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
    }

    @Test
    fun displaysWelcomeText() {
        setScreen()
        composeTestRule.onNodeWithText("Bienvenido de nuevo").assertIsDisplayed()
    }

    @Test
    fun displaysEmailLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
    }

    @Test
    fun displaysPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
    }

    @Test
    fun displaysLoginButton() {
        setScreen()
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }

    @Test
    fun displaysForgotPasswordLink() {
        setScreen()
        composeTestRule.onNodeWithText("¿Olvidaste tu contraseña?").assertIsDisplayed()
    }

    @Test
    fun displaysCreateAccountLink() {
        setScreen()
        composeTestRule.onNodeWithText("Crear cuenta nueva").assertIsDisplayed()
    }

    @Test
    fun displaysTermsText() {
        setScreen()
        composeTestRule.onNodeWithText(
            "Al continuar, aceptas nuestros Términos de servicio y Política de privacidad"
        ).assertIsDisplayed()
    }

    @Test
    fun emailField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[0]
            .performTextInput("test@mail.com")
        composeTestRule.onNodeWithText("test@mail.com").assertIsDisplayed()
    }

    @Test
    fun passwordField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[1]
            .performTextInput("password123")
    }

    @Test
    fun emptyFields_showsValidationError() {
        setScreen()
        composeTestRule.onNodeWithText("Iniciar sesión")
            .performScrollTo().performClick()
        composeTestRule.onNodeWithText("Completa todos los campos").assertIsDisplayed()
    }

    @Test
    fun createAccountLink_callsCallback() {
        var clicked = false
        setScreen(onRegister = { clicked = true })
        composeTestRule.onNodeWithText("Crear cuenta nueva")
            .performScrollTo().performClick()
        assert(clicked)
    }

    @Test
    fun backButton_callsCallback() {
        var clicked = false
        setScreen(onBack = { clicked = true })
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }

    @Test
    fun displaysLogo() {
        setScreen()
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
    }

    @Test
    fun displaysDividerText() {
        setScreen()
        composeTestRule.onNodeWithText("  o regístrate aquí  ").assertIsDisplayed()
    }
}
