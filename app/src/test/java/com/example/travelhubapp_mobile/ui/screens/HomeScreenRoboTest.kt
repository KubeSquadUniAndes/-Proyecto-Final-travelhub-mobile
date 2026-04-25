package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class HomeScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysTitle() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onLogout = {}
                )
            }
        }
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
    }

    @Test
    fun displaysSubtitle() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onLogout = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Encuentra tu hotel ideal").assertIsDisplayed()
    }

    @Test
    fun displaysSearchForm() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onLogout = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Destino").assertIsDisplayed()
        composeTestRule.onNodeWithText("Check-in").assertIsDisplayed()
        composeTestRule.onNodeWithText("Check-out").assertIsDisplayed()
        composeTestRule.onNodeWithText("Huéspedes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Buscar hoteles").assertIsDisplayed()
    }
}
