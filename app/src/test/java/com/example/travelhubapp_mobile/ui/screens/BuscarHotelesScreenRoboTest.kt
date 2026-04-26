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
        
        composeTestRule.onNodeWithText("Resultados").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bogotá • 2026-05-05").assertIsDisplayed()
        composeTestRule.onNodeWithText("0 habitaciones encontradas").assertIsDisplayed()
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

        // Verify hotel and room name
        composeTestRule.onNodeWithText("Grand Hotel Luxury").assertIsDisplayed()
        composeTestRule.onNodeWithText("Habitación 101").assertIsDisplayed()
        
        // Verify destination highlight
        composeTestRule.onNodeWithText("Cartagena").assertIsDisplayed()
        
        // Verify capacity and beds
        composeTestRule.onNodeWithText("2 pers.").assertIsDisplayed()
        composeTestRule.onNodeWithText("2 camas sencillas").assertIsDisplayed()
        
        // Verify price
        composeTestRule.onNodeWithText("$600000").assertIsDisplayed()
        
        // Verify amenities chips
        composeTestRule.onNodeWithText("WiFi").assertIsDisplayed()
        composeTestRule.onNodeWithText("TV").assertIsDisplayed()
        composeTestRule.onNodeWithText("AC").assertIsDisplayed()
    }

    @Test
    fun roomCard_clickTriggersAction() {
        var clicked = false
        val mockRoom = RoomResponse(
            id = "1",
            hotelId = "h1",
            hotelName = "Hotel Test",
            destination = "Destino",
            name = "Room Test",
            roomType = "Tipo",
            price = "100",
            capacity = 1,
            beds = "1 bed",
            size = 10.0,
            status = "ok",
            amenities = "none",
            createdAt = null,
            updatedAt = null
        )

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                RoomCard(room = mockRoom, onClick = { clicked = true })
            }
        }

        composeTestRule.onNodeWithText("Ver detalle").performClick()
        assert(clicked)
    }

    @Test
    fun header_backButton_triggersCallback() {
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
