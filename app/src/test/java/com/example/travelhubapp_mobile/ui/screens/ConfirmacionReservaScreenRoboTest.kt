package com.example.travelhubapp_mobile.ui.screens

import android.content.Context
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.atomic.AtomicBoolean

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
        viewModel.skipNetworkForTests = true
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

        composeTestRule.onNodeWithText("¡Reserva confirmada!").assertExists()
        composeTestRule.onNodeWithText("Tu reserva ha sido procesada exitosamente").assertExists()
        composeTestRule.onNodeWithText("#TH-2026-CONF").assertExists()
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

        composeTestRule.onNodeWithText("#123456").assertExists()
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

        composeTestRule
            .onNodeWithText("Confirmación enviada: Hemos enviado los detalles de tu reserva a tu correo electrónico")
            .assertExists()
        composeTestRule.onNodeWithText("¿Necesitas ayuda?").assertExists()
        composeTestRule.onNodeWithText("+57 300 123 4567").assertExists()
    }

    @Test
    fun homeButton_triggersCallback() {
        val homeClicked = AtomicBoolean(false)
        composeTestRule.setContent {
            ConfirmacionReservaScreen(
                onHome = { homeClicked.set(true) },
                viewModel = viewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("btn_back_home", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)
        composeTestRule.waitForIdle()

        assertTrue("onHome callback was not triggered", homeClicked.get())
    }
}
