package com.example.travelhubapp_mobile.ui.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ReservaPrintScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    private val mockBooking = BookingResponse(
        id = "b1",
        bookingCode = "TH-PRINT-TEST",
        statusDisplay = "Reserva Confirmada",
        travelerName = "Juan Test",
        travelerEmail = "juan@test.com",
        travelerPhone = "+57 300 000 0000",
        numGuests = 2,
        totalNights = 1,
        pricePerNight = "600000",
        totalPrice = "600000",
        taxes = "114000",
        finalPrice = "714000",
        roomType = "Deluxe"
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        viewModel.skipNetworkForTests = true
        viewModel.selectedBookingDetails = mockBooking
    }

    private fun setScreen(onBack: () -> Unit = {}, onHome: () -> Unit = {}) {
        viewModel.selectedBookingDetails = mockBooking
        composeTestRule.setContent {
            ReservaPrintScreen(
                bookingId = "b1",
                onBack = onBack,
                onHome = onHome,
                viewModel = viewModel
            )
        }
        composeTestRule.waitForIdle()
        // Restore booking after LaunchedEffect clears it in test mode
        if (viewModel.selectedBookingDetails == null) {
            viewModel.selectedBookingDetails = mockBooking
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun displaysConfirmationBanner() {
        setScreen()
        composeTestRule.onNodeWithText("#TH-PRINT-TEST", substring = true).assertExists()
    }

    @Test
    fun displaysGuestInformation() {
        setScreen()
        composeTestRule.onNodeWithText("Juan Test").assertExists()
    }

    @Test
    fun displaysPaymentSummary() {
        setScreen()
        composeTestRule.onAllNodesWithText("600000", substring = true).onFirst().assertExists()
        composeTestRule.onAllNodesWithText("714000", substring = true).onFirst().assertExists()
    }

    @Test
    fun displaysQRSection() {
        setScreen()
        composeTestRule.onNodeWithText("Código QR Check-in").assertExists()
    }

    @Test
    fun displaysQrPendingMessage_whenNoQrCode() {
        setScreen()
        composeTestRule.onNodeWithText("El hotel debe aprobar tu reserva").assertExists()
    }

    @Test
    fun displaysInvalidQrMessage_whenQrIsInvalid() {
        val invalidBooking = mockBooking.copy(qrCode = "somebase64", qrIsValid = false)
        viewModel.selectedBookingDetails = invalidBooking
        composeTestRule.setContent {
            ReservaPrintScreen(
                bookingId = "b1",
                onBack = {},
                onHome = {},
                viewModel = viewModel
            )
        }
        composeTestRule.waitForIdle()
        if (viewModel.selectedBookingDetails == null) {
            viewModel.selectedBookingDetails = invalidBooking
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Reserva cancelada o invalidada").assertExists()
    }

    @Test
    fun backButton_triggersCallback() {
        var clicked = false
        setScreen(onBack = { clicked = true })
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }

    @Test
    fun volverAlInicioButton_triggersCallback() {
        var clicked = false
        setScreen(onHome = { clicked = true })
        composeTestRule.onNodeWithText("Volver al inicio").performScrollTo().performClick()
        assert(clicked)
    }
}
