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
class RegistroScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setScreen(onRegister: () -> Unit = {}, onLogin: () -> Unit = {}, onBack: () -> Unit = {}) {
        composeTestRule.setContent { TravelHubAppMobileTheme { RegistroScreen(onRegister, onLogin, onBack) } }
    }

    @Test
    fun displaysTitle() {
        setScreen()
        composeTestRule.onAllNodesWithText("Crear cuenta").onFirst().assertExists()
    }

    @Test
    fun displaysSubtitle() {
        setScreen()
        composeTestRule.onNodeWithText("Únete a TravelHub y descubre tu próximo destino").assertExists()
    }

    @Test
    fun displaysLogo() {
        setScreen()
        composeTestRule.onNodeWithText("TH").assertExists()
    }

    @Test
    fun displaysNameLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Nombre completo").assertExists()
    }

    @Test
    fun displaysEmailLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Correo electrónico").assertExists()
    }

    @Test
    fun displaysPhoneLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Teléfono móvil").assertExists()
    }

    @Test
    fun displaysIdNumberLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Número de identificación").assertExists()
    }

    @Test
    fun displaysPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Contraseña").assertExists()
    }

    @Test
    fun displaysConfirmPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Confirmar contraseña").assertExists()
    }

    @Test
    fun displaysCreateAccountButton() {
        setScreen()
        composeTestRule.onAllNodesWithText("Crear cuenta").assertCountEquals(2)
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
        composeTestRule.onNodeWithText("Juan Pérez").assertExists()
    }

    @Test
    fun emailField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[1].performTextInput("juan@mail.com")
        composeTestRule.onNodeWithText("juan@mail.com").assertExists()
    }

    @Test
    fun phoneField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[2].performTextInput("3009999999")
        composeTestRule.onNodeWithText("3009999999").assertExists()
    }

    @Test
    fun idNumberField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[3].performTextInput("1234567890")
        composeTestRule.onNodeWithText("1234567890").assertExists()
    }

    @Test
    fun emptyFields_showsError() {
        setScreen()
        composeTestRule.onAllNodesWithText("Crear cuenta")[1].performScrollTo().performClick()
        composeTestRule.onNodeWithText("Completa todos los campos").assertExists()
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
        composeTestRule.onNodeWithText("Inicia sesión").performScrollTo().performClick()
        assert(clicked)
    }

    @Test
    fun displaysNamePlaceholder() {
        setScreen()
        composeTestRule.onNodeWithText("Juan Pérez García").assertExists()
    }

    @Test
    fun displaysEmailPlaceholder() {
        setScreen()
        composeTestRule.onNodeWithText("correo@ejemplo.com").assertExists()
    }

    @Test
    fun displaysIdNumberPlaceholder() {
        setScreen()
        composeTestRule.onNodeWithText("1234567890").assertExists()
    }
}
