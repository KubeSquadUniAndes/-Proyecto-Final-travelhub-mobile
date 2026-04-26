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
        viewModel.skipNetworkForTests = true
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
        composeTestRule.onNodeWithText("TravelHub").assertExists()
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
        composeTestRule.onNodeWithTag("input_destino", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("input_guests", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("btn_search").assertExists()
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
        
        composeTestRule.onNodeWithTag("input_destino", useUnmergedTree = true).performTextInput("Cartagena")
        assert(viewModel.destino == "Cartagena")
    }

    @Test
    fun guestsInput_validation() {
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
        
        val guestsNode = composeTestRule.onNodeWithTag("input_guests", useUnmergedTree = true)
        
        guestsNode.performTextReplacement("5")
        assert(viewModel.guests == "5")

        guestsNode.performTextReplacement("25")
        assert(viewModel.guests == "5") // should not update if > 20
    }

    @Test
    fun bottomBar_navigation() {
        var profileClicked = false
        var misReservasClicked = false
        
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HomeScreen(
                    onReservar = {},
                    onPerfil = { profileClicked = true },
                    onMisReservas = { misReservasClicked = true },
                    onLogout = {},
                    viewModel = viewModel
                )
            }
        }
        
        composeTestRule.onNode(hasText("Reservas") and hasClickAction()).performClick()
        assert(misReservasClicked)
        
        composeTestRule.onNode(hasText("Perfil") and hasClickAction()).performClick()
        assert(profileClicked)
    }
}
