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
class MisReservasScreenRoboTest {

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
    fun displaysHeader() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                MisReservasScreen(
                    onHome = {},
                    onPerfil = {},
                    onBuscarMas = {},
                    onBookingClick = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Mis Reservas").assertIsDisplayed()
        composeTestRule.onNodeWithText("0 reservas encontradas").assertIsDisplayed()
    }

    @Test
    fun displaysEmptyState() {
        // ViewModel is empty by default
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                MisReservasScreen(
                    onHome = {},
                    onPerfil = {},
                    onBuscarMas = {},
                    onBookingClick = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("No tienes reservas aún").assertIsDisplayed()
    }

    @Test
    fun displaysBookingList() {
        // We need to bypass private set for myBookings
        // Since reflection on Compose state is complex, 
        // we'll test the ReservaCard component directly
        val mockBooking = BookingResponse(
            id = "b1",
            bookingCode = "TH-MOCK-1",
            roomType = "Suite Presidencial",
            pricePerNight = "1200000"
        )

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("TH-MOCK-1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Suite Presidencial").assertIsDisplayed()
        composeTestRule.onNodeWithText("COP 1200000").assertIsDisplayed()
    }

    @Test
    fun bookingCard_clickTriggersCallback() {
        var clicked = false
        val mockBooking = BookingResponse(id = "b1", bookingCode = "CODE")

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = { clicked = true })
            }
        }

        // Tapping the card (which is the root of ReservaCard)
        composeTestRule.onNodeWithText("CODE").performClick()
        assert(clicked)
    }

    @Test
    fun buscarMasButton_triggersCallback() {
        var buscarMasClicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                MisReservasScreen(
                    onHome = {},
                    onPerfil = {},
                    onBuscarMas = { buscarMasClicked = true },
                    onBookingClick = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Buscar más hoteles").performClick()
        assert(buscarMasClicked)
    }

    @Test
    fun bottomBar_navigation() {
        var homeClicked = false
        var perfilClicked = false
        
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                MisReservasScreen(
                    onHome = { homeClicked = true },
                    onPerfil = { perfilClicked = true },
                    onBuscarMas = {},
                    onBookingClick = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Inicio").performClick()
        assert(homeClicked)

        composeTestRule.onNodeWithText("Perfil").performClick()
        assert(perfilClicked)
    }
}
