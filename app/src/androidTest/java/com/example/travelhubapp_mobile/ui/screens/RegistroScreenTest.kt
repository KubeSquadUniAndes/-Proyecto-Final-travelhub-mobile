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
        composeTestRule.onAllNodesWithText("Crear cuenta")[0].assertIsDisplayed()
    }

    @Test
    fun displaysSubtitle() {
        setScreen()
        composeTestRule.onNodeWithText("Completa tus datos para continuar")
            .assertIsDisplayed()
    }

    @Test
    fun displaysLogo() {
        setScreen()
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
    }

    @Test
    fun displaysInfoHeader() {
        setScreen()
        composeTestRule.onNodeWithText("Información personal").assertIsDisplayed()
    }

    @Test
    fun displaysFirstNameLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Nombre *").assertIsDisplayed()
    }

    @Test
    fun displaysLastNameLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Apellido *").assertIsDisplayed()
    }

    @Test
    fun displaysEmailLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Email *").assertIsDisplayed()
    }

    @Test
    fun displaysPhoneLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Teléfono *").assertIsDisplayed()
    }

    @Test
    fun displaysCountryLabel() {
        setScreen()
        composeTestRule.onNodeWithText("País *").assertExists()
    }

    @Test
    fun displaysCityLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Ciudad *").assertExists()
    }

    @Test
    fun displaysBirthDateLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Fecha de nacimiento *").assertExists()
    }

    @Test
    fun displaysIdTypeLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Tipo de ID *").assertExists()
    }

    @Test
    fun displaysIdNumberLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Número de ID *").assertExists()
    }

    @Test
    fun displaysPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Contraseña *").assertExists()
    }

    @Test
    fun displaysConfirmPasswordLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Confirmar contraseña *").assertExists()
    }

    @Test
    fun displaysDefaultIdType() {
        setScreen()
        composeTestRule.onNodeWithText("Cédula (CC)")
            .performScrollTo().assertIsDisplayed()
    }

    @Test
    fun displaysLoginLink() {
        setScreen()
        composeTestRule.onNodeWithText("Inicia sesión").assertExists()
    }

    @Test
    fun firstNameField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[0]
            .performTextInput("Juan")
        composeTestRule.onNodeWithText("Juan").assertIsDisplayed()
    }

    @Test
    fun lastNameField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[1]
            .performTextInput("Pérez")
        composeTestRule.onNodeWithText("Pérez").assertIsDisplayed()
    }

    @Test
    fun emptyFields_showsValidationError() {
        setScreen()
        composeTestRule.onAllNodesWithText("Crear cuenta")[1]
            .performScrollTo().performClick()
        composeTestRule.onNodeWithText("Completa todos los campos")
            .assertIsDisplayed()
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
        composeTestRule.onNodeWithText("Inicia sesión")
            .performScrollTo().performClick()
        assert(clicked)
    }
}
