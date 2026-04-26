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

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        viewModel.skipNetworkForTests = true

        viewModel.selectedBookingDetails = BookingResponse(
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
    }

    @Test
    fun displaysConfirmationBanner() {
        composeTestRule.setContent {
            ReservaPrintScreen(
                bookingId = "b1",
                onBack = {},
                onHome = {},
                viewModel = viewModel
            )
        }

        composeTestRule.waitForIdle()
        // Check for the number with #
        composeTestRule.onNodeWithText("#TH-PRINT-TEST", substring = true).assertExists()
    }

    @Test
    fun displaysGuestInformation() {
        composeTestRule.setContent {
            ReservaPrintScreen(
                bookingId = "b1",
                onBack = {},
                onHome = {},
                viewModel = viewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Juan Test").assertExists()
    }

    @Test
    fun displaysPaymentSummary() {
        composeTestRule.setContent {
            ReservaPrintScreen(
                bookingId = "b1",
                onBack = {},
                onHome = {},
                viewModel = viewModel
            )
        }

        composeTestRule.waitForIdle()
        // Verify substrings exist in the tree
        composeTestRule.onAllNodesWithText("600000", substring = true).onFirst().assertExists()
        composeTestRule.onAllNodesWithText("714000", substring = true).onFirst().assertExists()
    }

    @Test
    fun displaysQRSection() {
        composeTestRule.setContent {
            ReservaPrintScreen(
                bookingId = "b1",
                onBack = {},
                onHome = {},
                viewModel = viewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Código QR Check-in").assertExists()
    }
}
