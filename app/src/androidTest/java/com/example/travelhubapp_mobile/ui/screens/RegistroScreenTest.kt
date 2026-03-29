package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test

class RegistroScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setScreen(
        onRegister: () -> Unit = {},
        onLogin: () -> Unit = {},
        onBack: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { RegistroScreen(onRegister, onLogin, onBack) }
        }
    }

    @Test
    fun displaysTitle() {
        setScreen()
        composeTestRule.onNodeWithText("Crear cuenta").assertIsDisplayed()
    }

    @Test
    fun displaysSubtitle() {
        setScreen()
        composeTestRule.onNodeWithText("Únete a TravelHub y descubre tu próximo destino").assertIsDisplayed()
    }

    @Test
    fun displaysLogo() {
        setScreen()
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
    }

    @Test
    fun displaysNameLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Nombre completo").assertIsDisplayed()
    }

    @Test
    fun displaysEmailLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
    }

    @Test
    fun displaysPhoneLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Teléfono móvil").assertIsDisplayed()
    }

    @Test
    fun displaysPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
    }

    @Test
    fun displaysConfirmPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Confirmar contraseña").assertIsDisplayed()
    }

    @Test
    fun displaysCreateAccountButton() {
        setScreen()
        // Scroll to make sure button is visible
        composeTestRule.onNodeWithText("Crear cuenta", useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun displaysLoginLink() {
        setScreen()
        composeTestRule.onNodeWithText("Inicia sesión").assertExists()
    }

    @Test
    fun displaysAlreadyHaveAccountText() {
        setScreen()
        composeTestRule.onNodeWithText("¿Ya tienes cuenta? ").assertExists()
    }

    @Test
    fun nameField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[0].performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Juan Pérez").assertIsDisplayed()
    }

    @Test
    fun emailField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[1].performTextInput("juan@mail.com")
        composeTestRule.onNodeWithText("juan@mail.com").assertIsDisplayed()
    }

    @Test
    fun phoneField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[2].performTextInput("3001234567")
        composeTestRule.onNodeWithText("3001234567").assertIsDisplayed()
    }

    @Test
    fun backButton_callsCallback() {
        var clicked = false
        setScreen(onBack = { clicked = true })
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }

    @Test
    fun loginLink_callsCallback() {
        var clicked = false
        setScreen(onLogin = { clicked = true })
        composeTestRule.onNodeWithText("Inicia sesión").performClick()
        assert(clicked)
    }

    @Test
    fun displaysNamePlaceholder() {
        setScreen()
        composeTestRule.onNodeWithText("Juan Pérez García").assertIsDisplayed()
    }

    @Test
    fun displaysEmailPlaceholder() {
        setScreen()
        composeTestRule.onNodeWithText("correo@ejemplo.com").assertIsDisplayed()
    }

    @Test
    fun displaysPhonePlaceholder() {
        setScreen()
        composeTestRule.onNodeWithText("3001234567").assertIsDisplayed()
    }
}
