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

class BuscarHotelesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
        viewModel.destino = "Cartagena"
        viewModel.checkIn = "2026-06-01T12:00:00"
    }

    private fun setScreen(onHotelClick: () -> Unit = {}) {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                BuscarHotelesScreen(
                    onBack = {}, onHotelClick = onHotelClick, viewModel = viewModel
                )
            }
        }
    }

    @Test
    fun displaysResultsHeader() {
        setScreen()
        composeTestRule.onNodeWithText("Resultados").assertIsDisplayed()
    }

    @Test
    fun displaysRoomCount() {
        setScreen()
        composeTestRule.onNodeWithText("0 habitaciones encontradas").assertIsDisplayed()
    }

    @Test
    fun backButton_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                BuscarHotelesScreen(
                    onBack = { clicked = true }, onHotelClick = {}, viewModel = viewModel
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(clicked)
    }

    @Test
    fun roomCard_isDisplayed() {
        viewModel.roomResults = listOf(
            RoomResponse("r1", "h1", "Hotel Cartagena", null, "Suite Deluxe",
                "doble", "350000", 2, "1 cama", 30.0, "disponible", "WiFi", null, null)
        )
        setScreen()
        composeTestRule.onNodeWithTag("room_card_r1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hotel Cartagena").assertIsDisplayed()
    }

    @Test
    fun roomCard_verDetalle_triggersCallback() {
        var clicked = false
        viewModel.roomResults = listOf(
            RoomResponse("r1", "h1", "Hotel Test", null, "Suite",
                "doble", "200000", 2, null, null, "disponible", null, null, null)
        )
        setScreen(onHotelClick = { clicked = true })
        composeTestRule.onNodeWithText("Ver detalle").performClick()
        assert(clicked)
    }
}
