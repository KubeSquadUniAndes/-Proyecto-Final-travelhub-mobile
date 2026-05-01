package com.example.travelhubapp_mobile.ui.screens

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.RoomImageResponse
import com.example.travelhubapp_mobile.network.RoomResponse
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HabitacionDetalleScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: HotelViewModel

    private val mockRoom = RoomResponse(
        id = "room-123",
        hotelId = "hotel-1",
        hotelName = "Hotel Cartagena",
        destination = "Cartagena",
        name = "Habitación Deluxe",
        roomType = "doble",
        price = "350000",
        capacity = 2,
        beds = "1 cama doble",
        size = 30.0,
        status = "disponible",
        amenities = "WiFi, AC, TV",
        createdAt = null,
        updatedAt = null
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
        viewModel.selectedRoom = mockRoom
    }

    private fun setScreen(onBack: () -> Unit = {}, onReservar: () -> Unit = {}) {
        composeTestRule.setContent {
            TravelHubAppMobileTheme {
                HabitacionDetalleScreen(onBack = onBack, onReservar = onReservar, viewModel = viewModel)
            }
        }
    }

    @Test
    fun displaysHotelName() {
        setScreen()
        composeTestRule.onNodeWithText("Hotel Cartagena").assertIsDisplayed()
    }

    @Test
    fun displaysRoomName() {
        setScreen()
        composeTestRule.onNodeWithText("Habitación Deluxe").assertIsDisplayed()
    }

    @Test
    fun displaysCapacity() {
        setScreen()
        composeTestRule.onNodeWithText("2 personas").assertIsDisplayed()
    }

    @Test
    fun displaysBeds() {
        setScreen()
        composeTestRule.onNodeWithText("1 cama doble").assertIsDisplayed()
    }

    @Test
    fun displaysPrice() {
        setScreen()
        composeTestRule.onNodeWithText("\$350000").assertIsDisplayed()
    }

    @Test
    fun displaysReservarButton() {
        setScreen()
        composeTestRule.onNodeWithTag("btn_reservar").assertIsDisplayed()
    }

    @Test
    fun reservarButton_triggersCallback() {
        var clicked = false
        setScreen(onReservar = { clicked = true })
        composeTestRule.onNodeWithTag("btn_reservar").performClick()
        assert(clicked)
    }

    @Test
    fun backButton_triggersCallback() {
        var clicked = false
        setScreen(onBack = { clicked = true })
        composeTestRule.onNodeWithContentDescription("Volver").performClick()
        assert(clicked)
    }

    @Test
    fun displaysGallerySection() {
        setScreen()
        composeTestRule.onNodeWithTag("galeria_imagenes").assertExists()
    }

    @Test
    fun displaysAmenities() {
        setScreen()
        composeTestRule.onNodeWithTag("amenity_WiFi").assertExists()
    }

    @Test
    fun displaysReviewsCard() {
        setScreen()
        composeTestRule.onNodeWithTag("resena_card").assertIsDisplayed()
    }

    @Test
    fun displaysStatusBadge() {
        setScreen()
        composeTestRule.onNodeWithText("Disponible").assertIsDisplayed()
    }

    @Test
    fun withImages_galleryIsVisible() {
        viewModel.roomImages = listOf(
            RoomImageResponse(
                id = "img-1",
                roomId = "room-123",
                url = "https://example.com/img.jpg",
                createdAt = null
            )
        )
        setScreen()
        composeTestRule.onNodeWithTag("galeria_imagenes").assertIsDisplayed()
    }
}
