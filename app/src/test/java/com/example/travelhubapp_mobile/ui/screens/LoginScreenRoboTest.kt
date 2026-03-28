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
class LoginScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setScreen(onLogin: () -> Unit = {}, onRegister: () -> Unit = {}, onBack: () -> Unit = {}) {
        composeTestRule.setContent { TravelHubAppMobileTheme { LoginScreen(onLogin, onRegister, onBack) } }
    }

    @Test
    fun displaysAppName() {
        setScreen()
        composeTestRule.onNodeWithText("TravelHub").assertExists()
    }

    @Test
    fun displaysLogo() {
        setScreen()
        composeTestRule.onNodeWithText("TH").assertExists()
    }

    @Test
    fun displaysWelcomeText() {
        setScreen()
        composeTestRule.onNodeWithText("Bienvenido de nuevo").assertExists()
    }

    @Test
    fun displaysEmailLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Correo electrónico").assertExists()
    }

    @Test
    fun displaysPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Contraseña").assertExists()
    }

    @Test
    fun displaysLoginButton() {
        setScreen()
        composeTestRule.onAllNodesWithText("Iniciar sesión").assertCountEquals(1)
    }

    @Test
    fun displaysForgotPassword() {
        setScreen()
        composeTestRule.onNodeWithText("¿Olvidaste tu contraseña?").assertExists()
    }

    @Test
    fun displaysCreateAccountLink() {
        setScreen()
        composeTestRule.onNodeWithText("Crear cuenta nueva").assertExists()
    }

    @Test
    fun displaysDividerText() {
        setScreen()
        composeTestRule.onNodeWithText("  o regístrate aquí  ").assertExists()
    }

    @Test
    fun displaysTermsText() {
        setScreen()
        composeTestRule.onNodeWithText("Al continuar, aceptas nuestros Términos de servicio y Política de privacidad").assertExists()
    }

    @Test
    fun emailField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[0].performTextInput("test@mail.com")
        composeTestRule.onNodeWithText("test@mail.com").assertExists()
    }

    @Test
    fun passwordField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[1].performTextInput("password123")
    }

    @Test
    fun loginButton_callsCallback() {
        var clicked = false
        setScreen(onLogin = { clicked = true })
        composeTestRule.onAllNodesWithText("Iniciar sesión")[0].performScrollTo().performClick()
        assert(clicked)
    }

    @Test
    fun createAccountLink_callsCallback() {
        var clicked = false
        setScreen(onRegister = { clicked = true })
        composeTestRule.onNodeWithText("Crear cuenta nueva").performScrollTo().performClick()
        assert(clicked)
    }

    @Test
    fun backButton_callsCallback() {
        var clicked = false
        setScreen(onBack = { clicked = true })
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }
}
