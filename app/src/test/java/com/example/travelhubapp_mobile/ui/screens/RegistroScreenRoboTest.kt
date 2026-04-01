package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
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
        composeTestRule.onAllNodesWithText("Crear cuenta").onFirst().assertExists()
    }

    @Test
    fun displaysSubtitle() {
        setScreen()
        composeTestRule.onNodeWithText("Completa tus datos para continuar").assertExists()
    }

    @Test
    fun displaysLogo() {
        setScreen()
        composeTestRule.onNodeWithText("TH").assertExists()
    }

    @Test
    fun displaysFirstNameLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Nombre *").assertExists()
    }

    @Test
    fun displaysLastNameLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Apellido *").assertExists()
    }

    @Test
    fun displaysEmailLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Email *").assertExists()
    }

    @Test
    fun displaysPhoneLabel() {
        setScreen()
        composeTestRule.onNodeWithText("Teléfono *").assertExists()
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
    fun displaysInfoHeader() {
        setScreen()
        composeTestRule.onNodeWithText("Información personal").assertExists()
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
    fun firstNameField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[0].performTextInput("Juan")
        composeTestRule.onNodeWithText("Juan").assertExists()
    }

    @Test
    fun lastNameField_acceptsInput() {
        setScreen()
        composeTestRule.onAllNodes(hasSetTextAction())[1].performTextInput("Pérez")
        composeTestRule.onNodeWithText("Pérez").assertExists()
    }

    @Test
    fun emptyFields_showsError() {
        setScreen()
        composeTestRule.onAllNodesWithText("Crear cuenta")[1]
            .performScrollTo().performClick()
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
        composeTestRule.onNodeWithText("Inicia sesión")
            .performScrollTo().performClick()
        assert(clicked)
    }

    @Test
    fun displaysDefaultIdType() {
        setScreen()
        composeTestRule.onNodeWithText("Cédula (CC)").assertIsDisplayed()
    }
}
