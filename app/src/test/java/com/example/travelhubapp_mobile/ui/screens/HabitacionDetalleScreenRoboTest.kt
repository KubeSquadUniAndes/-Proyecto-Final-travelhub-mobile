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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class HabitacionDetalleScreenRoboTest {

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
        composeTestRule.onNodeWithTag("detalle_hotel_nombre").assertExists()
        composeTestRule.onNodeWithText("Hotel Cartagena").assertExists()
    }

    @Test
    fun displaysRoomName() {
        setScreen()
        composeTestRule.onNodeWithTag("detalle_habitacion_nombre").assertExists()
        composeTestRule.onNodeWithText("Habitación Deluxe").assertExists()
    }

    @Test
    fun displaysCapacityRow() {
        setScreen()
        composeTestRule.onNodeWithTag("detalle_capacidad_row").assertExists()
        composeTestRule.onNodeWithText("2 personas").assertExists()
    }

    @Test
    fun displaysBeds() {
        setScreen()
        composeTestRule.onNodeWithText("1 cama doble").assertExists()
    }

    @Test
    fun displaysSize() {
        setScreen()
        composeTestRule.onNodeWithText("30 m²").assertExists()
    }

    @Test
    fun displaysAmenities() {
        setScreen()
        composeTestRule.onNodeWithTag("amenity_WiFi").assertExists()
        composeTestRule.onNodeWithTag("amenity_AC").assertExists()
        composeTestRule.onNodeWithTag("amenity_TV").assertExists()
    }

    @Test
    fun displaysPrice() {
        setScreen()
        composeTestRule.onNodeWithTag("detalle_precio").assertExists()
        composeTestRule.onNodeWithText("\$350000").assertExists()
    }

    @Test
    fun displaysStatus() {
        setScreen()
        composeTestRule.onNodeWithText("Disponible").assertExists()
    }

    @Test
    fun displaysReservarButton() {
        setScreen()
        composeTestRule.onNodeWithTag("btn_reservar").assertExists()
        composeTestRule.onNodeWithText("Reservar").assertExists()
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
    fun displaysReviewsSection() {
        setScreen()
        composeTestRule.onNodeWithTag("resena_card").assertExists()
    }

    @Test
    fun displaysRoomTypeChip() {
        setScreen()
        composeTestRule.onNodeWithText("Doble").assertExists()
    }

    @Test
    fun withImages_displaysGallery() {
        viewModel.roomImages = listOf(
            RoomImageResponse(
                id = "img-1",
                roomId = "room-123",
                url = "https://example.com/img.jpg",
                createdAt = null
            )
        )
        setScreen()
        composeTestRule.onNodeWithTag("galeria_imagenes").assertExists()
    }
}
