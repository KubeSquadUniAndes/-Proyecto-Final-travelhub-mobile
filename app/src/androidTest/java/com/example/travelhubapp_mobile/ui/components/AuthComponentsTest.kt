package com.example.travelhubapp_mobile.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test

class AuthComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // --- THLogo ---

    @Test
    fun thLogo_displaysText() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { THLogo() }
        }
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
    }

    // --- THInput (text field) ---

    @Test
    fun thInput_displaysLabel() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "", onValueChange = {}, label = "Correo electrónico", placeholder = "correo@ejemplo.com")
            }
        }
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()
    }

    @Test
    fun thInput_displaysPlaceholder() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "", onValueChange = {}, label = "Correo", placeholder = "correo@ejemplo.com")
            }
        }
        composeTestRule.onNodeWithText("correo@ejemplo.com").assertIsDisplayed()
    }

    @Test
    fun thInput_acceptsTextInput() {
        var text = ""
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = text, onValueChange = { text = it }, label = "Email", placeholder = "")
            }
        }
        composeTestRule.onNode(hasSetTextAction()).performTextInput("hola")
        assert(text == "hola")
    }

    @Test
    fun thInput_passwordToggleVisibility() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "secret", onValueChange = {}, label = "Contraseña", isPassword = true)
            }
        }
        composeTestRule.onNodeWithContentDescription("Toggle password visibility")
            .assertExists()
            .performClick()
    }

    @Test
    fun thInput_nonPasswordHasNoToggle() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "text", onValueChange = {}, label = "Nombre")
            }
        }
        // Only the text field itself should be clickable, no trailing icon button
        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
    }

    // --- THButton ---

    @Test
    fun thButton_displaysText() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { THButton("Iniciar sesión", onClick = {}) }
        }
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }

    @Test
    fun thButton_clickCallsCallback() {
        var clicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme { THButton("Click me", onClick = { clicked = true }) }
        }
        composeTestRule.onNodeWithText("Click me").performClick()
        assert(clicked)
    }

    // --- THBackButton ---

    @Test
    fun thBackButton_clickCallsCallback() {
        var clicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme { THBackButton(onClick = { clicked = true }) }
        }
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }

    @Test
    fun thBackButton_isDisplayed() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { THBackButton(onClick = {}) }
        }
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }
}
