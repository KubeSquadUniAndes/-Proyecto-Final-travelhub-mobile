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
        viewModel.skipNetworkForTests = true
        
        viewModel.checkIn = "2026-05-01T12:00:00"
        viewModel.checkOut = "2026-05-04T12:00:00" // 3 nights
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
            updatedAt = null
        )
    }

    @Test
    fun displaysReservationSummary() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("Resumen de la reserva").assertExists()
        composeTestRule.onNodeWithText("Test Hotel").assertExists()
        composeTestRule.onNodeWithText("3").assertExists()
    }

    @Test
    fun displaysPersonalDataFields_usingTags() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithTag("input_nombre", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("input_email", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("input_telefono", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("input_documento", useUnmergedTree = true).assertExists()
    }

    @Test
    fun displaysPaymentSummaryInFooter() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        // Just verify the tag exists and contains digits to avoid locale issues
        composeTestRule.onNodeWithTag("total_price").assertTextContains("1", substring = true)
        composeTestRule.onNodeWithTag("total_price").assertTextContains("800", substring = true)
    }

    @Test
    fun inputFields_acceptText() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaScreen(onBack = {}, onSuccess = {}, viewModel = viewModel)
            }
        }

        val nameNode = composeTestRule.onNodeWithTag("input_nombre", useUnmergedTree = true)
        nameNode.performTextInput("Juan Test")
        nameNode.assertTextContains("Juan Test")
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
