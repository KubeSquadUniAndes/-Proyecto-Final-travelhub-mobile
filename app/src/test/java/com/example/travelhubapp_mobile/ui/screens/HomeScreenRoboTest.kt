package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
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
    fun displaysProfileHeader() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen(onLogout = {}) }
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun displaysLogoutButton() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen(onLogout = {}) }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Cerrar sesión").assertExists()
    }
}
