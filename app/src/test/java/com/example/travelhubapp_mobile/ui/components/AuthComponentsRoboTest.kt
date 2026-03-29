package com.example.travelhubapp_mobile.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
class AuthComponentsRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun thLogo_displaysText() {
        composeTestRule.setContent { TravelHubAppMobileTheme { THLogo() } }
        composeTestRule.onNodeWithText("TH").assertIsDisplayed()
    }

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
                THInput(value = text, onValueChange = { text = it }, label = "Email")
            }
        }
        composeTestRule.onNode(hasSetTextAction()).performTextInput("hola")
        assert(text == "hola")
    }

    @Test
    fun thInput_passwordMode_showsToggle() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "secret", onValueChange = {}, label = "Contraseña", isPassword = true)
            }
        }
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
    }

    @Test
    fun thInput_passwordToggle_click() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "secret", onValueChange = {}, label = "Pass", isPassword = true)
            }
        }
        // Click the visibility toggle icon
        composeTestRule.onAllNodes(hasClickAction())[0].performClick()
    }

    @Test
    fun thInput_nonPassword_displaysLabel() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "text", onValueChange = {}, label = "Nombre")
            }
        }
        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
    }

    @Test
    fun thInput_withLeadingIcon() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                THInput(value = "", onValueChange = {}, label = "Email", leadingIcon = Icons.Filled.Email)
            }
        }
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
    }

    @Test
    fun thButton_displaysText() {
        composeTestRule.setContent { TravelHubAppMobileTheme { THButton("Iniciar sesión", onClick = {}) } }
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }

    @Test
    fun thButton_clickCallsCallback() {
        var clicked = false
        composeTestRule.setContent { TravelHubAppMobileTheme { THButton("Click", onClick = { clicked = true }) } }
        composeTestRule.onNodeWithText("Click").performClick()
        assert(clicked)
    }

    @Test
    fun thBackButton_isDisplayed() {
        composeTestRule.setContent { TravelHubAppMobileTheme { THBackButton(onClick = {}) } }
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    @Test
    fun thBackButton_clickCallsCallback() {
        var clicked = false
        composeTestRule.setContent { TravelHubAppMobileTheme { THBackButton(onClick = { clicked = true }) } }
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }
}
