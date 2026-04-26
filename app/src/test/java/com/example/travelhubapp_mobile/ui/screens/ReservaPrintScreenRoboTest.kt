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
class ReservaPrintScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        
        // Mock the selected booking details
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
            roomType = "Deluxe",
        )
    }

    @Test
    fun displaysConfirmationBanner() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaPrintScreen(
                    bookingId = "b1",
                    onBack = {},
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Reserva Confirmada").assertIsDisplayed()
        composeTestRule.onNodeWithText("Número de reserva: #TH-PRINT-TEST").assertIsDisplayed()
    }

    @Test
    fun displaysReservationDetails() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaPrintScreen(
                    bookingId = "b1",
                    onBack = {},
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Detalles de la reserva").assertIsDisplayed()
        composeTestRule.onNodeWithText("2 personas").assertIsDisplayed()
        composeTestRule.onNodeWithText("1 noche").assertIsDisplayed()
    }

    @Test
    fun displaysGuestInformation() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaPrintScreen(
                    bookingId = "b1",
                    onBack = {},
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Información del huésped").assertIsDisplayed()
        composeTestRule.onNodeWithText("Juan Test").assertIsDisplayed()
        composeTestRule.onNodeWithText("juan@test.com").assertIsDisplayed()
    }

    @Test
    fun displaysPaymentSummary() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaPrintScreen(
                    bookingId = "b1",
                    onBack = {},
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Resumen de pago").assertIsDisplayed()
        composeTestRule.onNodeWithText("COP 600000").assertIsDisplayed()
        composeTestRule.onNodeWithText("COP 114000").assertIsDisplayed()
        composeTestRule.onNodeWithText("COP 714000").assertIsDisplayed()
    }

    @Test
    fun displaysQRSection() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaPrintScreen(
                    bookingId = "b1",
                    onBack = {},
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Código QR Check-in").assertIsDisplayed()
        composeTestRule.onNodeWithText("Escanea al llegar al hotel").assertIsDisplayed()
    }

    @Test
    fun backButton_triggersCallback() {
        var backClicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaPrintScreen(
                    bookingId = "b1",
                    onBack = { backClicked = true },
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }
}
