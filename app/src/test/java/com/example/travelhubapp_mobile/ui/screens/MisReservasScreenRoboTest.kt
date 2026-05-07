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
class MisReservasScreenRoboTest {

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
    fun displaysHeader() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                MisReservasScreen(
                    onHome = {},
                    onPerfil = {},
                    onBuscarMas = {},
                    onBookingClick = {},
                    onPagar = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("Mis Reservas").assertExists()
        composeTestRule.onNodeWithText("0 reservas encontradas").assertExists()
    }

    @Test
    fun displaysEmptyState() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                MisReservasScreen(
                    onHome = {},
                    onPerfil = {},
                    onBuscarMas = {},
                    onBookingClick = {},
                    onPagar = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("No tienes reservas aún").assertExists()
    }

    @Test
    fun displaysBookingList() {
        val mockBooking = BookingResponse(
            id = "b1",
            bookingCode = "TH-MOCK-1",
            roomType = "Suite Presidencial",
            pricePerNight = "1200000"
        )

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = {}, onPagar = {})
            }
        }

        composeTestRule.onNodeWithText("TH-MOCK-1").assertExists()
        composeTestRule.onNodeWithText("Suite Presidencial").assertExists()
        composeTestRule.onNodeWithText("COP 1200000").assertExists()
    }

    @Test
    fun bookingCard_clickTriggersCallback() {
        var clicked = false
        val mockBooking = BookingResponse(id = "b1", bookingCode = "CODE")

        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = { clicked = true }, onPagar = {})
            }
        }

        composeTestRule.onNodeWithText("CODE").performClick()
        assertTrue(clicked)
    }

    @Test
    fun bookingCard_pendingStatus_showsPagarButton() {
        val mockBooking = BookingResponse(
            id = "b1",
            bookingCode = "TH-MOCK-1",
            status = "pending",
            paymentId = null,
            pricePerNight = "150"
        )
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = {}, onPagar = {})
            }
        }
        composeTestRule.onNodeWithTag("btn_pagar_b1").assertExists()
    }

    @Test
    fun bookingCard_confirmedStatus_hidesPagarButton() {
        val mockBooking = BookingResponse(
            id = "b1",
            bookingCode = "TH-MOCK-1",
            status = "confirmed",
            pricePerNight = "150"
        )
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = {}, onPagar = {})
            }
        }
        composeTestRule.onNodeWithTag("btn_pagar_b1").assertDoesNotExist()
    }

    @Test
    fun bookingCard_pendingWithPaymentId_hidesPagarButton() {
        val mockBooking = BookingResponse(
            id = "b1",
            bookingCode = "TH-MOCK-1",
            status = "pending",
            paymentId = "payment-existing-123",
            pricePerNight = "150"
        )
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = {}, onPagar = {})
            }
        }
        composeTestRule.onNodeWithTag("btn_pagar_b1").assertDoesNotExist()
    }

    @Test
    fun bookingCard_pagarButton_triggersCallback() {
        var pagarClicked = false
        val mockBooking = BookingResponse(
            id = "b1",
            bookingCode = "TH-MOCK-1",
            status = "pending",
            pricePerNight = "150"
        )
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                ReservaCard(booking = mockBooking, onClick = {}, onPagar = { pagarClicked = true })
            }
        }
        composeTestRule.onNodeWithTag("btn_pagar_b1").performClick()
        assertTrue(pagarClicked)
    }

    @Test
    fun buscarMasButton_triggersCallback() {
        val buscarMasClicked = AtomicBoolean(false)
        composeTestRule.setContent {
            MisReservasScreen(
                onHome = {},
                onPerfil = {},
                onBuscarMas = { buscarMasClicked.set(true) },
                onBookingClick = {},
                onPagar = {},
                onLogout = {},
                viewModel = viewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("btn_buscar_mas", useUnmergedTree = true)
            .assertHasClickAction()
            .performSemanticsAction(SemanticsActions.OnClick)
        composeTestRule.waitForIdle()

        assertTrue("onBuscarMas callback was not triggered", buscarMasClicked.get())
    }

    @Test
    fun bottomBar_navigation() {
        val homeClicked = AtomicBoolean(false)
        val perfilClicked = AtomicBoolean(false)

        composeTestRule.setContent {
            MisReservasScreen(
                onHome = { homeClicked.set(true) },
                onPerfil = { perfilClicked.set(true) },
                onBuscarMas = {},
                onBookingClick = {},
                onPagar = {},
                onLogout = {},
                viewModel = viewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNode(hasText("Inicio") and hasClickAction()).performClick()
        composeTestRule.waitForIdle()
        assertTrue(homeClicked.get())

        composeTestRule.onNode(hasText("Perfil") and hasClickAction()).performClick()
        composeTestRule.waitForIdle()
        assertTrue(perfilClicked.get())
    }
}
