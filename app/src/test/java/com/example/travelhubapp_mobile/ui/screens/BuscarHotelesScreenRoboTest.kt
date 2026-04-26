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
class BuscarHotelesScreenRoboTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        viewModel.skipNetworkForTests = true
    }

    @Test
    fun displaysBasicHeaderInfo() {
        viewModel.destino = "Bogotá"
        viewModel.checkIn = "2026-05-05T12:00:00"
        
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                BuscarHotelesScreen(
                    onBack = {},
                    onHotelClick = {},
                    viewModel = viewModel
                )
            }
        }
        
        composeTestRule.onNodeWithText("Resultados").assertExists()
        composeTestRule.onNodeWithText("Bogotá • 2026-05-05").assertExists()
    }

    @Test
    fun roomCard_displaysDetailedInformation() {
        val mockRoom = RoomResponse(
            id = "1",
            hotelId = "h1",
            hotelName = "Grand Hotel Luxury",
            destination = "Cartagena",
            name = "Habitación 101",
            roomType = "Doble",
            price = "600000",
            capacity = 2,
            beds = "2 camas sencillas",
            size = 30.0,
            status = "disponible",
            amenities = "WiFi, TV, AC",
            createdAt = null,
            updatedAt = null
        )

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                RoomCard(room = mockRoom, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("Grand Hotel Luxury").assertExists()
        composeTestRule.onNodeWithText("Habitación 101").assertExists()
        composeTestRule.onNodeWithText("Cartagena").assertExists()
    }

    @Test
    fun displaysResultsFromViewModel() {
        val mockRoom = RoomResponse(
            id = "room_id_123",
            hotelId = "h1",
            hotelName = "Hotel Mock",
            destination = "Destino",
            name = "Room Mock",
            roomType = "Tipo",
            price = "500",
            capacity = 2,
            beds = "1 bed",
            size = 10.0,
            status = "ok",
            amenities = "WiFi",
            createdAt = null,
            updatedAt = null
        )
        viewModel.roomResults = listOf(mockRoom)

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                BuscarHotelesScreen(
                    onBack = {},
                    onHotelClick = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("1 habitaciones encontradas").assertExists()
        composeTestRule.onNodeWithTag("room_card_room_id_123").assertExists()
    }

    @Test
    fun backButton_triggersCallback() {
        var backClicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                BuscarHotelesScreen(
                    onBack = { backClicked = true },
                    onHotelClick = {},
                    viewModel = viewModel
                )
            }
        }
        
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }
}
