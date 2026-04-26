package com.example.travelhubapp_mobile.ui.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
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
class HomeScreenRoboTest {

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
    fun displaysBasicInfo() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onMisReservas = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }
        composeTestRule.onNodeWithText("TravelHub").assertIsDisplayed()
        composeTestRule.onNodeWithText("Encuentra tu hotel ideal").assertIsDisplayed()
    }

    @Test
    fun displaysSearchFormFields() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onMisReservas = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }
        composeTestRule.onNodeWithText("Destino").assertIsDisplayed()
        composeTestRule.onNodeWithText("Check-in").assertIsDisplayed()
        composeTestRule.onNodeWithText("Check-out").assertIsDisplayed()
        composeTestRule.onNodeWithText("Huéspedes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Buscar hoteles").assertIsDisplayed()
    }

    @Test
    fun destinationInput_updatesViewModel() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onMisReservas = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }
        
        // Find by placeholder
        val destinationNode = composeTestRule.onNodeWithText("¿A dónde viajas?")
        destinationNode.performTextInput("Cartagena")
        
        assert(viewModel.destino == "Cartagena")
    }

    @Test
    fun guestsInput_onlyAcceptsNumbersAndCapsAt20() {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onMisReservas = {},
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }
        
        // Use placeholder to find the node
        val guestsNode = composeTestRule.onNodeWithText("Máximo 20")
        
        // Test non-numeric input (should be ignored by logic in HomeScreen)
        guestsNode.performTextInput("abc")
        assert(viewModel.guests == "2") // remains default

        // Test valid numeric input
        guestsNode.performTextReplacement("5")
        assert(viewModel.guests == "5")

        // Test value > 20 (should be ignored by logic)
        guestsNode.performTextReplacement("25")
        assert(viewModel.guests == "5") // remains 5
    }

    @Test
    fun bottomBar_triggersNavigation() {
        var profileClicked = false
        var myBookingsClicked = false
        
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = { profileClicked = true },
                    onMisReservas = { myBookingsClicked = true },
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }
        
        // "Reservas" is the label for MisReservas in the bottom bar
        composeTestRule.onNodeWithText("Reservas").performClick()
        assert(myBookingsClicked)
        
        // "Perfil" is the label for Perfil in the bottom bar
        composeTestRule.onNodeWithText("Perfil").performClick()
        assert(profileClicked)
    }

    @Test
    fun logout_triggersCallback() {
        var logoutClicked = false
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = {},
                    onMisReservas = {},
                    onLogout = { logoutClicked = true },
                    viewModel = viewModel
                )
            }
        }
        
        composeTestRule.onNodeWithText("Salir").performClick()
        assert(logoutClicked)
    }
}
