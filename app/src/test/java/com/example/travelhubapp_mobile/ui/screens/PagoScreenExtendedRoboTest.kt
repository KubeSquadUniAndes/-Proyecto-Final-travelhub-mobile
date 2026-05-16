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
class PagoScreenExtendedRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    private val fullBooking = BookingResponse(
        id = "booking-456",
        bookingCode = "TH-2026-XYZ99",
        roomType = "suite",
        status = "pending",
        statusDisplay = "Pendiente de pago",
        totalNights = 2,
        pricePerNight = "300.00",
        totalPrice = "600.00",
        taxes = "108.00",
        finalPrice = "708.00"
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
        viewModel.selectedBookingForPayment = fullBooking
    }

    private fun setScreen(onBack: () -> Unit = {}, onSuccess: () -> Unit = {}) {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                PagoScreen(onBack = onBack, onSuccess = onSuccess, viewModel = viewModel)
            }
        }
    }

    @Test
    fun displaysResumenTitle() {
        setScreen()
        composeTestRule.onNodeWithText("Resumen de reserva").assertExists()
    }

    @Test
    fun displaysDatosDePagoTitle() {
        setScreen()
        composeTestRule.onNodeWithText("Datos de pago").assertExists()
    }

    @Test
    fun displaysVencimientoField() {
        setScreen()
        composeTestRule.onNodeWithText("Vencimiento").assertExists()
    }

    @Test
    fun displaysCvvField() {
        setScreen()
        composeTestRule.onNodeWithText("CVV").assertExists()
    }

    @Test
    fun displaysRoomType_capitalized() {
        setScreen()
        composeTestRule.onNodeWithText("Suite").assertExists()
    }

    @Test
    fun displaysNightsCount() {
        setScreen()
        composeTestRule.onNodeWithText("2").assertExists()
    }

    @Test
    fun expiryField_acceptsInput() {
        setScreen()
        composeTestRule.onNodeWithText("MM/AA").performTextInput("12/28")
        composeTestRule.onNodeWithText("12/28").assertExists()
    }

    @Test
    fun cvvField_acceptsInput() {
        setScreen()
        composeTestRule.onNodeWithText("123").performTextInput("456")
        composeTestRule.onNodeWithText("456").assertExists()
    }

    @Test
    fun confirmButton_withShortCardNumber_setsError() {
        setScreen()
        // Leave card number empty and click confirm
        composeTestRule.onNodeWithTag("input_card_number").performTextInput("123")
        composeTestRule.onNodeWithTag("btn_confirmar_pago").performClick()
        composeTestRule.waitForIdle()
        // Error about card number
        composeTestRule.onNodeWithText("Ingresa un número de tarjeta válido (16 dígitos)").assertExists()
    }

    @Test
    fun confirmButton_withValidCardNoName_setsError() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").performTextInput("1234567890123456")
        // Leave name blank
        composeTestRule.onNodeWithTag("btn_confirmar_pago").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("El nombre en la tarjeta es requerido").assertExists()
    }

    @Test
    fun confirmButton_validCardAndName_invalidEmail_setsError() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").performTextInput("1234567890123456")
        composeTestRule.onNodeWithTag("input_card_name").performTextInput("JUAN PEREZ")
        composeTestRule.onNodeWithTag("input_pago_email").performTextInput("notanemail")
        composeTestRule.onNodeWithTag("btn_confirmar_pago").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Ingresa un email válido").assertExists()
    }

    @Test
    fun confirmButton_validCardNameEmail_noExpiry_setsError() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").performTextInput("1234567890123456")
        composeTestRule.onNodeWithTag("input_card_name").performTextInput("JUAN PEREZ")
        composeTestRule.onNodeWithTag("input_pago_email").performTextInput("juan@test.com")
        // Leave expiry blank
        composeTestRule.onNodeWithTag("btn_confirmar_pago").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("La fecha de vencimiento es requerida").assertExists()
    }

    @Test
    fun confirmButton_validAll_butShortCvv_setsError() {
        setScreen()
        composeTestRule.onNodeWithTag("input_card_number").performTextInput("1234567890123456")
        composeTestRule.onNodeWithTag("input_card_name").performTextInput("JUAN PEREZ")
        composeTestRule.onNodeWithTag("input_pago_email").performTextInput("juan@test.com")
        composeTestRule.onNodeWithText("MM/AA").performTextInput("12/28")
        composeTestRule.onNodeWithText("123").performTextInput("12")
        composeTestRule.onNodeWithTag("btn_confirmar_pago").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("El CVV debe tener al menos 3 dígitos").assertExists()
    }

    @Test
    fun errorMessage_isDisplayedWhenSet() {
        setScreen()
        viewModel.error = "Error de prueba visible"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Error de prueba visible").assertExists()
    }

    @Test
    fun noBooking_displaysDefaultValues() {
        viewModel.selectedBookingForPayment = null
        setScreen()
        // Should show 0 or dashes for unknown values
        composeTestRule.onNodeWithTag("btn_confirmar_pago").assertExists()
    }

    @Test
    fun bookingCode_isDisplayedInResumen() {
        setScreen()
        composeTestRule.onNodeWithText("TH-2026-XYZ99").assertExists()
    }

    @Test
    fun totalAmount_isDisplayedCorrectly() {
        setScreen()
        composeTestRule.onNodeWithText("COP 708.00").assertExists()
    }
}
