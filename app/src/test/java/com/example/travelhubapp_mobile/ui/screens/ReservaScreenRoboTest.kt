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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ReservaScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        
        // Setup initial search state for price calculation
        viewModel.checkIn = "2026-05-01T12:00:00"
        viewModel.checkOut = "2026-05-04T12:00:00" // 3 nights
        viewModel.guests = "2"
        viewModel.selectedRoom = RoomResponse(
            id = "r1",
            hotelId = "h1",
            hotelName = "Test Hotel",
            destination = "Cartagena",
            name = "Room 101",
            roomType = "Deluxe",
            price = "600000",
            capacity = 2,
            beds = "1 King",
            size = 20.0,
            status = "ok",
            amenities = "none",
            createdAt = null,
            updatedAt = null,
        )
    }

    @Test
    fun displaysReservationSummary() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("Resumen de la reserva").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Hotel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Check-in:").assertIsDisplayed()
        composeTestRule.onNodeWithText("2026-05-01").assertIsDisplayed()
        composeTestRule.onNodeWithText("Noches:").assertIsDisplayed()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
    }

    @Test
    fun displaysPersonalDataFields() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("Datos personales").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre completo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Teléfono").assertIsDisplayed()
        composeTestRule.onNodeWithText("Número de documento").assertIsDisplayed()
    }

    @Test
    fun displaysPaymentSummaryInFooter() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        // 3 nights * 600,000 = 1,800,000
        composeTestRule.onNodeWithText("Total (3 noches):").assertIsDisplayed()
        composeTestRule.onNodeWithText("COP 1,800,000").assertIsDisplayed()
    }

    @Test
    fun inputFields_updateState() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("Juan Pérez").performTextInput("Juan Test")
        composeTestRule.onNodeWithText("1234567890").performTextInput("987654321")
        
        // These fields are internal state, so we just verify they accept input
        composeTestRule.onNodeWithText("Juan Test").assertExists()
        composeTestRule.onNodeWithText("987654321").assertExists()
    }

    @Test
    fun backButton_triggersCallback() {
        var backClicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = { backClicked = true }, onSuccess = {}, viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }
}
