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
class ConfirmacionReservaScreenRoboTest {

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
    fun displaysSuccessInfo() {
        viewModel.lastBooking = BookingResponse(
            id = "b1",
            bookingCode = "TH-2026-CONF",
            statusDisplay = "Confirmada"
        )

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ConfirmacionReservaScreen(
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("¡Reserva confirmada!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tu reserva ha sido procesada exitosamente").assertIsDisplayed()
        // Code is displayed as #TH-2026-CONF
        composeTestRule.onNodeWithText("#TH-2026-CONF").assertIsDisplayed()
    }

    @Test
    fun displaysFallbackBookingCodeWhenNull() {
        viewModel.lastBooking = null

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ConfirmacionReservaScreen(
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        // Current implementation uses "123456" as fallback
        composeTestRule.onNodeWithText("#123456").assertIsDisplayed()
    }

    @Test
    fun displaysInfoBoxAndContact() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ConfirmacionReservaScreen(
                    onHome = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Confirmación enviada: Hemos enviado los detalles de tu reserva a tu correo electrónico").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿Necesitas ayuda?").assertIsDisplayed()
        composeTestRule.onNodeWithText("+57 300 123 4567").assertIsDisplayed()
    }

    @Test
    fun homeButton_triggersCallback() {
        var homeClicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ConfirmacionReservaScreen(
                    onHome = { homeClicked = true },
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Volver al inicio").performClick()
        assert(homeClicked)
    }
}
