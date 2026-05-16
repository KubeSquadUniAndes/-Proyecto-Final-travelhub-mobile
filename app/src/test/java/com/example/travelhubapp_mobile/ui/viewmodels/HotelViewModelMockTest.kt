package com.example.travelhubapp_mobile.ui.viewmodels

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.network.RoomResponse
import kotlinx.coroutines.runBlocking
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
class HotelViewModelMockTest {

    private lateinit var viewModel: HotelViewModel
    private lateinit var tokenManager: TokenManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        viewModel.skipNetworkForTests = true
    }

    @Test
    fun createBooking_noRoom_doesNothing() {
        viewModel.selectedRoom = null
        viewModel.createBooking("Juan", "juan@test.com", "123", "456") {}
        assertNull(viewModel.lastBooking)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun selectedBookingForPayment_canBeSet() {
        val booking = BookingResponse(id = "b1", bookingCode = "TH-TEST", finalPrice = "714.00")
        viewModel.selectedBookingForPayment = booking
        assertEquals("b1", viewModel.selectedBookingForPayment?.id)
        assertEquals("714.00", viewModel.selectedBookingForPayment?.finalPrice)
    }

    @Test
    fun error_canBeSetAndCleared() {
        viewModel.error = "test error"
        assertEquals("test error", viewModel.error)
        viewModel.error = null
        assertNull(viewModel.error)
    }

    @Test
    fun skipNetworkForTests_preventsNetworkCalls() {
        viewModel.skipNetworkForTests = true
        viewModel.searchHotels {}
        viewModel.searchRooms {}
        viewModel.fetchBookings()
        viewModel.fetchRoomImages("r1")
        viewModel.fetchAllRoomImages(listOf("r1", "r2"))
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.searchResults.isEmpty())
        assertTrue(viewModel.roomResults.isEmpty())
        assertTrue(viewModel.myBookings.isEmpty())
    }

    @Test
    fun roomImagesMap_canBeUpdated() {
        viewModel.roomImagesMap = mapOf("r1" to "url1", "r2" to "url2")
        assertEquals("url1", viewModel.roomImagesMap["r1"])
        assertEquals("url2", viewModel.roomImagesMap["r2"])
        assertNull(viewModel.roomImagesMap["r3"])
    }

    @Test
    fun selectedRoom_withAllFields_storesCorrectly() {
        val room = RoomResponse(
            id = "r1", hotelId = "h1", hotelName = "Hotel Test",
            destination = "Bogotá", name = "Suite Deluxe", roomType = "doble",
            price = "350000", capacity = 4, beds = "2 camas",
            size = 45.0, status = "disponible", amenities = "WiFi,AC,TV",
            createdAt = "2026-01-01", updatedAt = "2026-01-01"
        )
        viewModel.selectedRoom = room
        assertEquals("r1", viewModel.selectedRoom?.id)
        assertEquals(4, viewModel.selectedRoom?.capacity)
        assertEquals(45.0, viewModel.selectedRoom?.size)
        assertEquals("WiFi,AC,TV", viewModel.selectedRoom?.amenities)
    }

    @Test
    fun noToken_viewModel_createsWithoutCrash() {
        runBlocking { tokenManager.clearToken() }
        val noTokenViewModel = HotelViewModel(tokenManager)
        noTokenViewModel.skipNetworkForTests = true
        assertNull(noTokenViewModel.error)
        assertFalse(noTokenViewModel.isLoading)
    }
}
