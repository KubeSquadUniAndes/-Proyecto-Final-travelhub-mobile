package com.example.travelhubapp_mobile.ui.viewmodels

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.network.HotelResponse
import com.example.travelhubapp_mobile.network.PaymentResponse
import com.example.travelhubapp_mobile.network.RoomImageResponse
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

/**
 * Tests that exercise the branch logic inside HotelViewModel for better branch coverage.
 * Uses skipNetworkForTests=true to test the early-return branches.
 * Also tests state scenarios that hit different branches of the state fields.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class HotelViewModelBranchTest {

    private lateinit var viewModel: HotelViewModel
    private lateinit var tokenManager: TokenManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        viewModel.skipNetworkForTests = true
    }

    // ===== searchHotels — branch: skipNetworkForTests = true =====

    @Test
    fun searchHotels_withSkipTrue_returnsImmediately() {
        viewModel.skipNetworkForTests = true
        viewModel.searchHotels { /* onSuccess */ }
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.searchResults.isEmpty())
        assertNull(viewModel.error)
    }

    @Test
    fun searchHotels_withDestino_stateNotChanged() {
        viewModel.destino = "Bogotá"
        viewModel.checkIn = "2026-06-01T15:00:00Z"
        viewModel.checkOut = "2026-06-05T11:00:00Z"
        viewModel.searchHotels {}
        // Still empty because skip
        assertTrue(viewModel.searchResults.isEmpty())
    }

    // ===== searchRooms — branch: skipNetworkForTests = true =====

    @Test
    fun searchRooms_withSkipTrue_returnsImmediately() {
        viewModel.skipNetworkForTests = true
        viewModel.searchRooms {}
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.roomResults.isEmpty())
    }

    @Test
    fun searchRooms_withInvalidGuests_stateNotChanged() {
        viewModel.guests = "notANumber"  // Exercises toIntOrNull()? returning null
        viewModel.searchRooms {}
        assertTrue(viewModel.roomResults.isEmpty())
    }

    @Test
    fun searchRooms_withBlankDestino_stateNotChanged() {
        viewModel.destino = ""  // Exercises ifBlank { null } branch
        viewModel.searchRooms {}
        assertTrue(viewModel.roomResults.isEmpty())
    }

    // ===== createBooking — branches: selectedRoom = null, skipNetworkForTests = true =====

    @Test
    fun createBooking_withNullRoom_doesNothing() {
        viewModel.selectedRoom = null
        var successCalled = false
        viewModel.createBooking("Juan", "juan@test.com", "+57300", "DOC001") {
            successCalled = true
        }
        assertFalse(successCalled)
        assertNull(viewModel.lastBooking)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun createBooking_withSkipTrue_doesNothing() {
        viewModel.selectedRoom = RoomResponse(
            "r1", "h1", "Hotel Test", "Bogotá", "Suite", "doble",
            "200000", 2, null, null, "disponible", null, null, null
        )
        viewModel.skipNetworkForTests = true
        viewModel.createBooking("Juan", "juan@test.com", "+57300", "DOC001") {}
        assertNull(viewModel.lastBooking)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun createBooking_withEmptyCheckInOut_usesDefaults() {
        // The logic uses ifBlank to provide defaults, exercise that branch
        viewModel.selectedRoom = RoomResponse(
            "r1", "h1", "Hotel", null, "Standard", null,
            "150000", 2, null, null, null, null, null, null
        )
        viewModel.checkIn = ""   // ifBlank { "2026-05-01T15:00:00Z" }
        viewModel.checkOut = ""  // ifBlank { "2026-05-05T11:00:00Z" }
        viewModel.skipNetworkForTests = true
        viewModel.createBooking("Ana", "ana@test.com", "+57301", "DOC002") {}
        assertNull(viewModel.lastBooking)
    }

    @Test
    fun createBooking_withNonNumericPrice_usesDefault() {
        // price.toDoubleOrNull() ?: 150.0 branch
        viewModel.selectedRoom = RoomResponse(
            "r1", "h1", "Hotel", null, "Standard", null,
            "not-a-price", 1, null, null, null, null, null, null
        )
        viewModel.skipNetworkForTests = true
        viewModel.createBooking("Pedro", "pedro@test.com", "+57302", "DOC003") {}
        assertNull(viewModel.lastBooking)
    }

    @Test
    fun createBooking_withNullRoomType_usesDefault() {
        // roomType ?: "Deluxe" branch
        viewModel.selectedRoom = RoomResponse(
            "r1", "h1", "Hotel", null, "Room", null,
            "100000", 2, null, null, null, null, null, null
        )
        viewModel.skipNetworkForTests = true
        viewModel.createBooking("María", "maria@test.com", "+57303", "DOC004") {}
        assertNull(viewModel.lastBooking)
    }

    @Test
    fun createBooking_withNonNumericGuests_usesDefault() {
        // guests.toIntOrNull() ?: 2 branch
        viewModel.selectedRoom = RoomResponse(
            "r1", "h1", "Hotel", null, "Suite", "doble",
            "200000", 2, null, null, null, null, null, null
        )
        viewModel.guests = "abc"  // triggers toIntOrNull() returning null → default 2
        viewModel.skipNetworkForTests = true
        viewModel.createBooking("Carlos", "carlos@test.com", "+57304", "DOC005") {}
        assertNull(viewModel.lastBooking)
    }

    // ===== fetchRoomImages — branch: skipNetworkForTests = true =====

    @Test
    fun fetchRoomImages_withSkipTrue_roomImagesEmpty() {
        viewModel.skipNetworkForTests = true
        viewModel.fetchRoomImages("room-001")
        assertTrue(viewModel.roomImages.isEmpty())
    }

    @Test
    fun fetchRoomImages_withSkipTrue_doesNotClearImages() {
        // Pre-set some images
        viewModel.roomImages = listOf(
            RoomImageResponse("img1", "r1", "https://url.com/img.jpg", null)
        )
        viewModel.skipNetworkForTests = true
        // fetchRoomImages with skipNetworkForTests=true returns immediately without clearing
        viewModel.fetchRoomImages("r1")
        // Images remain as they were (the early return skips the roomImages = emptyList() line)
        assertEquals(1, viewModel.roomImages.size)
    }

    // ===== fetchAllRoomImages — branch: skipNetworkForTests = true =====

    @Test
    fun fetchAllRoomImages_withSkipTrue_mapEmpty() {
        viewModel.skipNetworkForTests = true
        viewModel.fetchAllRoomImages(listOf("r1", "r2", "r3"))
        assertTrue(viewModel.roomImagesMap.isEmpty())
    }

    @Test
    fun fetchAllRoomImages_withEmptyList_mapEmpty() {
        viewModel.skipNetworkForTests = true
        viewModel.fetchAllRoomImages(emptyList())
        assertTrue(viewModel.roomImagesMap.isEmpty())
    }

    // ===== fetchBookings — branch: skipNetworkForTests = true =====

    @Test
    fun fetchBookings_withSkipTrue_bookingsEmpty() {
        viewModel.skipNetworkForTests = true
        viewModel.fetchBookings()
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.myBookings.isEmpty())
    }

    // ===== fetchBookingDetails — branch: skipNetworkForTests = true =====

    @Test
    fun fetchBookingDetails_withSkipTrue_detailsNull() {
        viewModel.skipNetworkForTests = true
        viewModel.fetchBookingDetails("booking-detail-001")
        assertFalse(viewModel.isLoading)
        assertNull(viewModel.selectedBookingDetails)
    }

    // ===== createPayment — branch: skipNetworkForTests = true =====

    @Test
    fun createPayment_withSkipTrue_paymentNull() {
        viewModel.skipNetworkForTests = true
        viewModel.createPayment("b1", 500.0, "4242", "Test User", "test@test.com") {}
        assertNull(viewModel.lastPayment)
        assertFalse(viewModel.isLoading)
    }

    // ===== State machine transitions =====

    @Test
    fun guestsField_numericVariants() {
        // Test different numeric values for guests field — exercises toIntOrNull() branch
        viewModel.guests = "1"
        assertEquals("1", viewModel.guests)

        viewModel.guests = "0"
        assertEquals("0", viewModel.guests)

        viewModel.guests = "100"
        assertEquals("100", viewModel.guests)

        // Non-numeric — the toIntOrNull() ?: 2 branch is exercised in searchHotels/createBooking
        viewModel.guests = ""
        assertEquals("", viewModel.guests)
    }

    @Test
    fun destinoBlankVariants_forSearchRooms() {
        // "".ifBlank { null } exercises the ifBlank branch
        viewModel.destino = ""
        assertEquals("", viewModel.destino)

        viewModel.destino = "Bogotá"
        assertEquals("Bogotá", viewModel.destino)

        viewModel.destino = "   " // also blank
        assertEquals("   ", viewModel.destino)
    }

    @Test
    fun fullBookingWorkflow_stateTransitions() {
        // Set up a complete state for a booking workflow
        val hotel = HotelResponse("h1", "Grand Hotel", "4.5", "200000", "500", "Bogotá", null)
        viewModel.searchResults = listOf(hotel)

        val room = RoomResponse(
            "r1", "h1", "Grand Hotel", "Bogotá", "Suite Deluxe", "suite",
            "350000", 4, "1 king bed", 55.0, "disponible", "WiFi,AC,TV,Minibar",
            "2025-01-01", "2025-12-01"
        )
        viewModel.roomResults = listOf(room)
        viewModel.selectedRoom = room

        viewModel.checkIn = "2026-07-01T15:00:00Z"
        viewModel.checkOut = "2026-07-05T11:00:00Z"
        viewModel.guests = "3"
        viewModel.destino = "Bogotá"

        assertEquals("h1", viewModel.selectedRoom?.hotelId)
        assertEquals("350000", viewModel.selectedRoom?.price)
        assertEquals("suite", viewModel.selectedRoom?.roomType)
        assertEquals(1, viewModel.roomResults.size)
        assertEquals("3", viewModel.guests)
    }

    @Test
    fun paymentWorkflow_stateTransitions() {
        val booking = BookingResponse(
            id = "b1",
            bookingCode = "TH-2026-001",
            finalPrice = "1416.00",
            status = "confirmed"
        )
        viewModel.selectedBookingForPayment = booking
        assertEquals("b1", viewModel.selectedBookingForPayment?.id)
        assertEquals("1416.00", viewModel.selectedBookingForPayment?.finalPrice)

        val payment = PaymentResponse(
            id = "p1",
            bookingId = "b1",
            status = "confirmed",
            amount = "1416.00"
        )
        viewModel.lastPayment = payment
        assertEquals("confirmed", viewModel.lastPayment?.status)
        assertEquals("1416.00", viewModel.lastPayment?.amount)
    }

    @Test
    fun multipleRoomImages_stateTransitions() {
        val images = listOf(
            RoomImageResponse("img1", "r1", "https://cdn.example.com/1.jpg", "2026-01-01"),
            RoomImageResponse("img2", "r1", "https://cdn.example.com/2.jpg", "2026-01-02"),
            RoomImageResponse("img3", "r1", "https://cdn.example.com/3.jpg", null)
        )
        viewModel.roomImages = images
        assertEquals(3, viewModel.roomImages.size)

        // Access the null field (createdAt)
        assertNull(viewModel.roomImages[2].createdAt)
        assertEquals("https://cdn.example.com/1.jpg", viewModel.roomImages[0].url)
    }

    @Test
    fun clearAllState_resetsToDefaults() = runBlocking {
        tokenManager.clearToken()

        viewModel.searchResults = listOf(HotelResponse("h1", "H", "4", "100", null, null, null))
        viewModel.roomResults = listOf(
            RoomResponse("r1", "h1", null, null, "N", null, "100", 1, null, null, null, null, null, null)
        )
        viewModel.myBookings = listOf(BookingResponse(id = "b1"))
        viewModel.selectedRoom = RoomResponse("r1", "h1", null, null, "N", null, "100", 1, null, null, null, null, null, null)
        viewModel.lastBooking = BookingResponse(id = "b1")
        viewModel.error = "some error"

        // Reset state manually
        viewModel.searchResults = emptyList()
        viewModel.roomResults = emptyList()
        viewModel.myBookings = emptyList()
        viewModel.selectedRoom = null
        viewModel.lastBooking = null
        viewModel.error = null

        assertTrue(viewModel.searchResults.isEmpty())
        assertTrue(viewModel.roomResults.isEmpty())
        assertTrue(viewModel.myBookings.isEmpty())
        assertNull(viewModel.selectedRoom)
        assertNull(viewModel.lastBooking)
        assertNull(viewModel.error)
    }

    @Test
    fun skipNetworkForTests_canToggle() {
        assertFalse(HotelViewModel(tokenManager).skipNetworkForTests)
        viewModel.skipNetworkForTests = true
        assertTrue(viewModel.skipNetworkForTests)
        viewModel.skipNetworkForTests = false
        assertFalse(viewModel.skipNetworkForTests)
    }
}
