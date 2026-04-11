package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
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
    fun displaysTitle() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
    }

    @Test
    fun displaysSubtitle() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.onNodeWithText("Hospedajes disponibles").assertIsDisplayed()
    }

    @Test
    fun displaysLoadingIndicator() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.onNodeWithText("Cargando hospedajes...").assertIsDisplayed()
    }

    @Test
    fun displaysHospedajesAfterLoading() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.onNodeWithText("Hotel Grand Luxury").assertIsDisplayed()
    }

    @Test
    fun displaysReservarButton() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.onNodeWithText("Reservar").assertIsDisplayed()
    }

    @Test
    fun displaysHospedajeCount() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.onNodeWithText("5 hospedajes disponibles").assertIsDisplayed()
    }

    @Test
    fun displaysHospedajeLocation() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.onNodeWithText("Bogotá, Colombia").assertIsDisplayed()
    }

    @Test
    fun displaysHospedajePrice() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme { HomeScreen() }
        }
        composeTestRule.mainClock.advanceTimeBy(2000)
        composeTestRule.onNodeWithText("COP 600,000").assertIsDisplayed()
    }
}
