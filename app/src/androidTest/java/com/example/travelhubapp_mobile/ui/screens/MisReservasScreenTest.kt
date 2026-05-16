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

class MisReservasScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
    }

    private fun setScreen(onPagar: (String) -> Unit = {}) {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                MisReservasScreen(
                    onHome = {}, onPerfil = {}, onBuscarMas = {},
                    onBookingClick = {}, onPagar = onPagar,
                    onLogout = {}, viewModel = viewModel
                )
            }
        }
    }

    @Test
    fun displaysHeader() {
        setScreen()
        composeTestRule.onNodeWithText("Mis Reservas").assertIsDisplayed()
    }

    @Test
    fun displaysEmptyState() {
        setScreen()
        composeTestRule.onNodeWithText("No tienes reservas aún").assertIsDisplayed()
    }

    @Test
    fun displaysBookingCount() {
        setScreen()
        composeTestRule.onNodeWithText("0 reservas encontradas").assertIsDisplayed()
    }

    @Test
    fun displaysBuscarMasButton() {
        setScreen()
        composeTestRule.onNodeWithText("Buscar más hoteles").assertIsDisplayed()
    }

    @Test
    fun pendingBooking_showsPagarButton() {
        viewModel.myBookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-TEST", status = "pending", paymentId = null)
        )
        setScreen()
        composeTestRule.onNodeWithTag("btn_pagar_b1").assertIsDisplayed()
    }

    @Test
    fun confirmedBooking_hidesPagarButton() {
        viewModel.myBookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-TEST", status = "confirmed")
        )
        setScreen()
        composeTestRule.onNodeWithTag("btn_pagar_b1").assertDoesNotExist()
    }

    @Test
    fun pendingWithPaymentId_hidesPagarButton() {
        viewModel.myBookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-TEST", status = "pending", paymentId = "p1")
        )
        setScreen()
        composeTestRule.onNodeWithTag("btn_pagar_b1").assertDoesNotExist()
    }

    @Test
    fun pagarButton_triggersCallback() {
        var pagarId = ""
        viewModel.myBookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-TEST", status = "pending", paymentId = null)
        )
        setScreen(onPagar = { pagarId = it })
        composeTestRule.onNodeWithTag("btn_pagar_b1").performClick()
        assert(pagarId == "b1")
    }

    @Test
    fun reservaCard_showsBookingCode() {
        viewModel.myBookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-2026-ABCDE", status = "confirmed")
        )
        setScreen()
        composeTestRule.onNodeWithText("TH-2026-ABCDE").assertIsDisplayed()
    }
}
