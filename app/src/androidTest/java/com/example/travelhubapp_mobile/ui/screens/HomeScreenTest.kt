package com.example.travelhubapp_mobile.ui.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
    }

    private fun setScreen() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {}, onPerfil = {},
                    onMisReservas = {}, onLogout = {},
                    viewModel = viewModel
                )
            }
        }
    }

    @Test
    fun displaysAppName() {
        setScreen()
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
    }

    @Test
    fun displaysHeroText() {
        setScreen()
        composeTestRule.onNodeWithText("Encuentra tu hotel ideal").assertIsDisplayed()
    }

    @Test
    fun displaysDestinoField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_destino").assertIsDisplayed()
    }

    @Test
    fun displaysGuestsField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_guests").assertIsDisplayed()
    }

    @Test
    fun displaysSearchButton() {
        setScreen()
        composeTestRule.onNodeWithTag("btn_search").assertIsDisplayed()
    }

    @Test
    fun missingCheckIn_showsValidationError() {
        setScreen()
        composeTestRule.onNodeWithTag("btn_search").performClick()
        composeTestRule.onNodeWithText("Por favor selecciona la fecha de check-in").assertIsDisplayed()
    }

    @Test
    fun missingCheckOut_showsValidationError() {
        setScreen()
        viewModel.checkIn = "2026-06-01T12:00:00"
        composeTestRule.onNodeWithTag("btn_search").performClick()
        composeTestRule.onNodeWithText("Por favor selecciona la fecha de check-out").assertIsDisplayed()
    }

    @Test
    fun destinoField_acceptsInput() {
        setScreen()
        composeTestRule.onNodeWithTag("input_destino").performTextInput("Cartagena")
        composeTestRule.onNodeWithText("Cartagena").assertIsDisplayed()
    }
}
