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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class PagoScreenRoboTest {

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
        composeTestRule.onNodeWithText("Pagar reserva").assertExists()
    }

    @Test
    fun displaysBookingCode() {
        setScreen()
        composeTestRule.onNodeWithText("TH-2026-ABCDE").assertExists()
    }

    @Test
    fun displaysStatus() {
        setScreen()
        composeTestRule.onNodeWithText("Pendiente de pago").assertExists()
    }

    @Test
    fun displaysTotalAmount() {
        setScreen()
        composeTestRule.onNodeWithTag("pago_total").assertExists()
        composeTestRule.onNodeWithText("COP 714.00").assertExists()
    }

    @Test
    fun displaysCardNumberField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").assertExists()
    }

    @Test
    fun displaysCardNameField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_name").assertExists()
    }

    @Test
    fun displaysEmailField() {
        setScreen()
        composeTestRule.onNodeWithTag("input_pago_email").assertExists()
    }

    @Test
    fun displaysConfirmButton() {
        setScreen()
        composeTestRule.onNodeWithTag("btn_confirmar_pago").assertExists()
        composeTestRule.onNodeWithText("Confirmar pago").assertExists()
    }

    @Test
    fun displaysSecurityText() {
        setScreen()
        composeTestRule.onNodeWithText("Pago seguro con encriptación SSL").assertExists()
    }

    @Test
    fun cardNumberField_acceptsInput() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").performTextInput("4242424242424242")
        composeTestRule.onNodeWithText("4242424242424242").assertExists()
    }

    @Test
    fun cardNameField_acceptsInput() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_name").performTextInput("JUAN PEREZ")
        composeTestRule.onNodeWithText("JUAN PEREZ").assertExists()
    }

    @Test
    fun backButton_triggersCallback() {
        var clicked = false
        setScreen(onBack = { clicked = true })
        composeTestRule.onNodeWithContentDescription("Volver").performClick()
        assert(clicked)
    }

    @Test
    fun displaysNights() {
        setScreen()
        composeTestRule.onNodeWithText("4").assertExists()
    }

    @Test
    fun noBooking_doesNotCrash() {
        viewModel.selectedBookingForPayment = null
        setScreen()
        composeTestRule.onNodeWithTag("btn_confirmar_pago").assertExists()
    }
}
