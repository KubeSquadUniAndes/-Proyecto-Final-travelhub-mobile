package com.example.travelhubapp_mobile.ui.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.RoomResponse
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReservaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
        viewModel.checkIn = "2026-06-01T12:00:00"
        viewModel.checkOut = "2026-06-05T12:00:00"
        viewModel.selectedRoom = RoomResponse(
            id = "r1", hotelId = "h1", hotelName = "Hotel Test",
            destination = null, name = "Suite", roomType = "doble",
            price = "150000", capacity = 2, beds = "1 cama",
            size = 30.0, status = "disponible", amenities = "WiFi",
            createdAt = null, updatedAt = null
        )
    }

    private fun setScreen() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }
    }

    @Test
    fun displaysTitle() {
        setScreen()
        composeTestRule.onNodeWithText("Finalizar reserva").assertIsDisplayed()
    }

    @Test
    fun displaysResumenCard() {
        setScreen()
        composeTestRule.onNodeWithText("Resumen de la reserva").assertIsDisplayed()
    }

    @Test
    fun displaysHotelName() {
        setScreen()
        composeTestRule.onNodeWithText("Hotel Test").assertIsDisplayed()
    }

    @Test
    fun displaysDatosPersonalesCard() {
        setScreen()
        composeTestRule.onNodeWithText("Datos personales").assertIsDisplayed()
    }

    @Test
    fun displaysNombreField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_nombre").assertIsDisplayed()
    }

    @Test
    fun displaysEmailField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_email").assertIsDisplayed()
    }

    @Test
    fun displaysTelefonoField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_telefono").assertIsDisplayed()
    }

    @Test
    fun displaysDocumentoField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_documento").assertIsDisplayed()
    }

    @Test
    fun displaysReservarButton() {
        setScreen()
        composeTestRule.onNodeWithText("Reservar").assertIsDisplayed()
    }

    @Test
    fun emptyFields_showsValidationError() {
        setScreen()
        composeTestRule.onNodeWithText("Reservar").performClick()
        composeTestRule.onNodeWithText("El nombre es requerido").assertIsDisplayed()
    }

    @Test
    fun invalidEmail_showsValidationError() {
        setScreen()
        composeTestRule.onNodeWithTag("input_nombre").performTextInput("Juan")
        composeTestRule.onNodeWithTag("input_email").performTextInput("invalidemail")
        composeTestRule.onNodeWithText("Reservar").performClick()
        composeTestRule.onNodeWithText("Ingresa un email válido").assertIsDisplayed()
    }

    @Test
    fun backButton_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = { clicked = true }, onSuccess = {}, viewModel = viewModel)
            }
        }
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }
}
