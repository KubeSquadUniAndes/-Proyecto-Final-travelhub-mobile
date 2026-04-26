package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class PerfilScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysProfileHeader() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(onLogout = {}, onHome = {}, onMisReservas = {})
            }
        }

        composeTestRule.onNodeWithText("Mi Perfil").assertExists()
        composeTestRule.onNodeWithText("Viajero TravelHub").assertExists()
        composeTestRule.onNodeWithText("8").assertExists()
        // Check for "Reservas" in stat card (no click action)
        composeTestRule.onNode(hasText("Reservas") and !hasClickAction()).assertExists()
    }

    @Test
    fun displaysRecentReservations() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(onLogout = {}, onHome = {}, onMisReservas = {})
            }
        }

        composeTestRule.onNodeWithText("Reservas recientes").assertExists()
        composeTestRule.onNodeWithText("Hotel Grand Luxury").assertExists()
        composeTestRule.onNodeWithText("Beachfront Paradise Resort").assertExists()
    }

    @Test
    fun displaysAccountMenu() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PerfilScreen(onLogout = {}, onHome = {}, onMisReservas = {})
            }
        }

        composeTestRule.onNodeWithText("Mi cuenta").assertExists()
        composeTestRule.onNodeWithText("Información personal").assertExists()
        composeTestRule.onNodeWithText("Métodos de pago").assertExists()
        composeTestRule.onNodeWithText("Notificaciones").assertExists()
        composeTestRule.onNodeWithText("Seguridad").assertExists()
    }

    @Test
    fun logoutButton_triggersCallback() {
        val logoutClicked = AtomicBoolean(false)
        composeTestRule.setContent {
            PerfilScreen(
                onLogout = { logoutClicked.set(true) },
                onHome = {},
                onMisReservas = {}
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("btn_logout", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)
        composeTestRule.waitForIdle()

        assertTrue("onLogout callback was not triggered", logoutClicked.get())
    }

    @Test
    fun bottomBar_navigation() {
        val homeClicked = AtomicBoolean(false)
        val misReservasClicked = AtomicBoolean(false)

        composeTestRule.setContent {
            PerfilScreen(
                onLogout = {},
                onHome = { homeClicked.set(true) },
                onMisReservas = { misReservasClicked.set(true) }
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNode(hasText("Inicio") and hasClickAction()).performClick()
        composeTestRule.waitForIdle()
        assertTrue(homeClicked.get())

        composeTestRule.onNode(hasText("Reservas") and hasClickAction()).performClick()
        composeTestRule.waitForIdle()
        assertTrue(misReservasClicked.get())
    }
}
