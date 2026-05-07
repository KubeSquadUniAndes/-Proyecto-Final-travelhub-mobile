package com.example.travelhubapp_mobile.ui.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PagoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    private val mockBooking = BookingResponse(
        id = "booking-123",
        bookingCode = "TH-2026-ABCDE",
        roomType = "individual",
        status = "pending",
        statusDisplay = "Pendiente de pago",
        totalNights = 4,
        pricePerNight = "150.00",
        totalPrice = "600.00",
        taxes = "114.00",
        finalPrice = "714.00"
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
        viewModel.selectedBookingForPayment = mockBooking
    }

    private fun setScreen(onBack: () -> Unit = {}, onSuccess: () -> Unit = {}) {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PagoScreen(onBack = onBack, onSuccess = onSuccess, viewModel = viewModel)
            }
        }
    }

    @Test
    fun displaysTitle() {
        setScreen()
        composeTestRule.onNodeWithText("Pagar reserva").assertIsDisplayed()
    }

    @Test
    fun displaysBookingCode() {
        setScreen()
        composeTestRule.onNodeWithText("TH-2026-ABCDE").assertIsDisplayed()
    }

    @Test
    fun displaysTotalAmount() {
        setScreen()
        composeTestRule.onNodeWithTag("pago_total").assertIsDisplayed()
    }

    @Test
    fun displaysConfirmButton() {
        setScreen()
        composeTestRule.onNodeWithTag("btn_confirmar_pago").assertIsDisplayed()
    }

    @Test
    fun displaysCardFields() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").assertIsDisplayed()
        composeTestRule.onNodeWithTag("input_card_name").assertIsDisplayed()
        composeTestRule.onNodeWithTag("input_pago_email").assertIsDisplayed()
    }

    @Test
    fun cardNumberField_acceptsInput() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").performTextInput("4242424242424242")
        composeTestRule.onNodeWithText("4242424242424242").assertIsDisplayed()
    }

    @Test
    fun backButton_triggersCallback() {
        var clicked = false
        setScreen(onBack = { clicked = true })
        composeTestRule.onNodeWithContentDescription("Volver").performClick()
        assert(clicked)
    }

    @Test
    fun displaysSecurityBadge() {
        setScreen()
        composeTestRule.onNodeWithText("Pago seguro con encriptación SSL").assertIsDisplayed()
    }

    @Test
    fun displaysStatus() {
        setScreen()
        composeTestRule.onNodeWithText("Pendiente de pago").assertIsDisplayed()
    }

    @Test
    fun confirmButton_isClickable() {
        setScreen()
        composeTestRule.onNodeWithTag("btn_confirmar_pago").assertHasClickAction()
    }
}
