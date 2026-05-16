package com.example.travelhubapp_mobile.ui.viewmodels

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.network.HotelResponse
import com.example.travelhubapp_mobile.network.RoomImageResponse
import com.example.travelhubapp_mobile.network.RoomResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class HotelViewModelTest {

    private lateinit var viewModel: HotelViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = HotelViewModel(TokenManager(context))
        viewModel.skipNetworkForTests = true
    }

    @Test
    fun initialState_isCorrect() {
        assertEquals("", viewModel.destino)
        assertEquals("", viewModel.checkIn)
        assertEquals("", viewModel.checkOut)
        assertEquals("2", viewModel.guests)
        assertTrue(viewModel.searchResults.isEmpty())
        assertTrue(viewModel.roomResults.isEmpty())
        assertNull(viewModel.selectedRoom)
        assertNull(viewModel.lastBooking)
        assertTrue(viewModel.myBookings.isEmpty())
        assertFalse(viewModel.isLoading)
        assertNull(viewModel.error)
    }

    @Test
    fun setDestino_updatesState() {
        viewModel.destino = "Cartagena"
        assertEquals("Cartagena", viewModel.destino)
    }

    @Test
    fun setCheckIn_updatesState() {
        viewModel.checkIn = "2026-06-01T12:00:00"
        assertEquals("2026-06-01T12:00:00", viewModel.checkIn)
    }

    @Test
    fun setCheckOut_updatesState() {
        viewModel.checkOut = "2026-06-05T12:00:00"
        assertEquals("2026-06-05T12:00:00", viewModel.checkOut)
    }

    @Test
    fun setGuests_updatesState() {
        viewModel.guests = "4"
        assertEquals("4", viewModel.guests)
    }

    @Test
    fun setSelectedRoom_updatesState() {
        val room = RoomResponse(
            id = "r1", hotelId = "h1", hotelName = "Hotel Test",
            destination = "Bogotá", name = "Suite", roomType = "doble",
            price = "200000", capacity = 2, beds = "1 cama",
            size = 30.0, status = "disponible", amenities = "WiFi",
            createdAt = null, updatedAt = null
        )
        viewModel.selectedRoom = room
        assertEquals("r1", viewModel.selectedRoom?.id)
        assertEquals("Hotel Test", viewModel.selectedRoom?.hotelName)
    }

    @Test
    fun setSearchResults_updatesState() {
        val hotels = listOf(
            HotelResponse("h1", "Hotel A", "4.5", "200000", "100", "Bogotá", null)
        )
        viewModel.searchResults = hotels
        assertEquals(1, viewModel.searchResults.size)
        assertEquals("Hotel A", viewModel.searchResults[0].name)
    }

    @Test
    fun setRoomResults_updatesState() {
        val rooms = listOf(
            RoomResponse("r1", "h1", "Hotel A", null, "Suite", "doble",
                "200000", 2, null, null, "disponible", null, null, null)
        )
        viewModel.roomResults = rooms
        assertEquals(1, viewModel.roomResults.size)
    }

    @Test
    fun setMyBookings_updatesState() {
        val bookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-2026-TEST", status = "pending")
        )
        viewModel.myBookings = bookings
        assertEquals(1, viewModel.myBookings.size)
        assertEquals("TH-2026-TEST", viewModel.myBookings[0].bookingCode)
    }

    @Test
    fun setRoomImages_updatesState() {
        val images = listOf(
            RoomImageResponse("img1", "r1", "https://example.com/img.jpg", null)
        )
        viewModel.roomImages = images
        assertEquals(1, viewModel.roomImages.size)
    }

    @Test
    fun setRoomImagesMap_updatesState() {
        viewModel.roomImagesMap = mapOf("r1" to "https://example.com/img.jpg")
        assertEquals("https://example.com/img.jpg", viewModel.roomImagesMap["r1"])
    }

    @Test
    fun setError_updatesState() {
        viewModel.error = "Error de prueba"
        assertEquals("Error de prueba", viewModel.error)
    }

    @Test
    fun clearError_setsNull() {
        viewModel.error = "Error"
        viewModel.error = null
        assertNull(viewModel.error)
    }

    @Test
    fun searchHotels_skipsNetworkWhenTestMode() {
        viewModel.searchHotels {}
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.searchResults.isEmpty())
    }

    @Test
    fun searchRooms_skipsNetworkWhenTestMode() {
        viewModel.searchRooms {}
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun fetchBookings_skipsNetworkWhenTestMode() {
        viewModel.fetchBookings()
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.myBookings.isEmpty())
    }

    @Test
    fun fetchRoomImages_skipsNetworkWhenTestMode() {
        viewModel.fetchRoomImages("room-123")
        assertTrue(viewModel.roomImages.isEmpty())
    }

    @Test
    fun fetchAllRoomImages_skipsNetworkWhenTestMode() {
        viewModel.fetchAllRoomImages(listOf("r1", "r2"))
        assertTrue(viewModel.roomImagesMap.isEmpty())
    }

    @Test
    fun createBooking_skipsNetworkWhenTestMode() {
        viewModel.selectedRoom = RoomResponse(
            "r1", "h1", "Hotel", null, "Suite", "doble",
            "200000", 2, null, null, null, null, null, null
        )
        viewModel.createBooking("Juan", "juan@test.com", "123", "456") {}
        assertNull(viewModel.lastBooking)
    }

    @Test
    fun createPayment_skipsNetworkWhenTestMode() {
        viewModel.createPayment("b1", 100.0, "4242", "Juan", "juan@test.com") {}
        assertNull(viewModel.lastPayment)
    }

    @Test
    fun selectedBookingForPayment_updatesState() {
        val booking = BookingResponse(id = "b1", bookingCode = "TH-2026-TEST")
        viewModel.selectedBookingForPayment = booking
        assertEquals("b1", viewModel.selectedBookingForPayment?.id)
    }

    @Test
    fun fetchBookingDetails_skipsNetworkWhenTestMode() {
        viewModel.fetchBookingDetails("booking-123")
        assertNull(viewModel.selectedBookingDetails)
    }

    @Test
    fun setLastPayment_updatesState() {
        assertNull(viewModel.lastPayment)
    }
}
