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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class HotelViewModelExtendedTest {

    private lateinit var viewModel: HotelViewModel
    private lateinit var tokenManager: TokenManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tokenManager = TokenManager(context)
        viewModel = HotelViewModel(tokenManager)
        viewModel.skipNetworkForTests = true
    }

    // === Destino, CheckIn, CheckOut, Guests field coverage ===

    @Test
    fun destino_canBeUpdatedMultipleTimes() {
        viewModel.destino = "Bogotá"
        assertEquals("Bogotá", viewModel.destino)
        viewModel.destino = "Cartagena"
        assertEquals("Cartagena", viewModel.destino)
        viewModel.destino = ""
        assertEquals("", viewModel.destino)
    }

    @Test
    fun checkIn_format_iso8601() {
        viewModel.checkIn = "2026-06-01T15:00:00Z"
        assertEquals("2026-06-01T15:00:00Z", viewModel.checkIn)
    }

    @Test
    fun checkOut_format_iso8601() {
        viewModel.checkOut = "2026-06-05T11:00:00Z"
        assertEquals("2026-06-05T11:00:00Z", viewModel.checkOut)
    }

    @Test
    fun guests_defaultIsTwo() {
        assertEquals("2", viewModel.guests)
    }

    @Test
    fun guests_canBeUpdatedToAnyValue() {
        viewModel.guests = "1"
        assertEquals("1", viewModel.guests)
        viewModel.guests = "10"
        assertEquals("10", viewModel.guests)
    }

    // === SearchResults ===

    @Test
    fun searchResults_canBeSetToMultipleHotels() {
        val hotels = listOf(
            HotelResponse("h1", "Hotel Alpha", "4.5", "200000", "100", "Bogotá", null),
            HotelResponse("h2", "Hotel Beta", "3.8", "150000", "50", "Cartagena", "https://img.com/h2.jpg"),
            HotelResponse("h3", "Hotel Gamma", "5.0", "300000", null, "Medellín", null)
        )
        viewModel.searchResults = hotels
        assertEquals(3, viewModel.searchResults.size)
        assertEquals("Hotel Alpha", viewModel.searchResults[0].name)
        assertEquals("Hotel Beta", viewModel.searchResults[1].name)
        assertEquals("Hotel Gamma", viewModel.searchResults[2].name)
    }

    @Test
    fun searchResults_canBeCleared() {
        viewModel.searchResults = listOf(
            HotelResponse("h1", "Hotel", "4.0", "100000", null, null, null)
        )
        assertEquals(1, viewModel.searchResults.size)
        viewModel.searchResults = emptyList()
        assertTrue(viewModel.searchResults.isEmpty())
    }

    // === RoomResults ===

    @Test
    fun roomResults_canBeSetWithFullRoom() {
        val room = RoomResponse(
            id = "r1",
            hotelId = "h1",
            hotelName = "Grand Hotel",
            destination = "Bogotá",
            name = "Suite Presidencial",
            roomType = "suite",
            price = "500000",
            capacity = 4,
            beds = "1 king bed",
            size = 80.0,
            status = "disponible",
            amenities = "WiFi,AC,TV,Minibar",
            createdAt = "2026-01-01",
            updatedAt = "2026-01-15"
        )
        viewModel.roomResults = listOf(room)
        assertEquals(1, viewModel.roomResults.size)
        assertEquals("Suite Presidencial", viewModel.roomResults[0].name)
        assertEquals(80.0, viewModel.roomResults[0].size)
        assertEquals("WiFi,AC,TV,Minibar", viewModel.roomResults[0].amenities)
    }

    @Test
    fun roomResults_canBeSetWithMinimalRoom() {
        val room = RoomResponse(
            id = "r2", hotelId = "h2", hotelName = null,
            destination = null, name = "Basic", roomType = null,
            price = "100000", capacity = 1, beds = null,
            size = null, status = null, amenities = null,
            createdAt = null, updatedAt = null
        )
        viewModel.roomResults = listOf(room)
        assertEquals(1, viewModel.roomResults.size)
        assertNull(viewModel.roomResults[0].hotelName)
        assertNull(viewModel.roomResults[0].amenities)
    }

    // === SelectedRoom ===

    @Test
    fun selectedRoom_canBeSetAndCleared() {
        val room = RoomResponse(
            "r1", "h1", "Hotel", null, "Suite", "doble",
            "200000", 2, null, null, "disponible", null, null, null
        )
        viewModel.selectedRoom = room
        assertNotNull(viewModel.selectedRoom)
        viewModel.selectedRoom = null
        assertNull(viewModel.selectedRoom)
    }

    @Test
    fun selectedRoom_priceField_isAccessible() {
        val room = RoomResponse(
            "r1", "h1", "Hotel Test", "Bogotá", "Deluxe Room", "doble",
            "285000", 2, "1 queen bed", 35.0, "disponible", "WiFi,AC",
            "2026-01-01", "2026-02-01"
        )
        viewModel.selectedRoom = room
        assertEquals("285000", viewModel.selectedRoom?.price)
        assertEquals(2, viewModel.selectedRoom?.capacity)
    }

    // === LastBooking ===

    @Test
    fun lastBooking_initiallyNull() {
        assertNull(viewModel.lastBooking)
    }

    @Test
    fun lastBooking_canBeSet() {
        val booking = BookingResponse(
            id = "b1",
            bookingCode = "TH-2026-001",
            status = "confirmed",
            travelerName = "Juan García",
            travelerEmail = "juan@test.com",
            travelerPhone = "+57300123",
            travelerDocument = "1234567890",
            numGuests = 2,
            roomType = "Deluxe",
            pricePerNight = "200000",
            totalNights = 4,
            totalPrice = "800000",
            taxes = "144000",
            finalPrice = "944000"
        )
        viewModel.lastBooking = booking
        assertNotNull(viewModel.lastBooking)
        assertEquals("TH-2026-001", viewModel.lastBooking?.bookingCode)
        assertEquals("Juan García", viewModel.lastBooking?.travelerName)
        assertEquals("944000", viewModel.lastBooking?.finalPrice)
    }

    // === MyBookings ===

    @Test
    fun myBookings_canContainMultiple() {
        val bookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-001", status = "confirmed"),
            BookingResponse(id = "b2", bookingCode = "TH-002", status = "pending"),
            BookingResponse(id = "b3", bookingCode = "TH-003", status = "cancelled")
        )
        viewModel.myBookings = bookings
        assertEquals(3, viewModel.myBookings.size)
        assertEquals("confirmed", viewModel.myBookings[0].status)
        assertEquals("pending", viewModel.myBookings[1].status)
        assertEquals("cancelled", viewModel.myBookings[2].status)
    }

    @Test
    fun myBookings_findById_works() {
        val bookings = listOf(
            BookingResponse(id = "b1", bookingCode = "TH-001"),
            BookingResponse(id = "b2", bookingCode = "TH-002")
        )
        viewModel.myBookings = bookings
        val found = viewModel.myBookings.find { it.id == "b2" }
        assertNotNull(found)
        assertEquals("TH-002", found?.bookingCode)
    }

    // === SelectedBookingDetails ===

    @Test
    fun selectedBookingDetails_initiallyNull() {
        assertNull(viewModel.selectedBookingDetails)
    }

    @Test
    fun selectedBookingDetails_canBeSet() {
        val details = BookingResponse(
            id = "b1",
            bookingCode = "TH-2026-001",
            status = "confirmed",
            qrCode = "base64QrData",
            qrIsValid = true,
            paymentId = "p1"
        )
        viewModel.selectedBookingDetails = details
        assertNotNull(viewModel.selectedBookingDetails)
        assertEquals("base64QrData", viewModel.selectedBookingDetails?.qrCode)
        assertEquals(true, viewModel.selectedBookingDetails?.qrIsValid)
    }

    // === RoomImages ===

    @Test
    fun roomImages_canContainMultiple() {
        val images = listOf(
            RoomImageResponse("img1", "r1", "https://s3.example.com/img1.jpg", "2026-01-01"),
            RoomImageResponse("img2", "r1", "https://s3.example.com/img2.jpg", null),
            RoomImageResponse("img3", "r1", "https://s3.example.com/img3.jpg", "2026-01-03")
        )
        viewModel.roomImages = images
        assertEquals(3, viewModel.roomImages.size)
        assertEquals("https://s3.example.com/img1.jpg", viewModel.roomImages[0].url)
    }

    @Test
    fun roomImagesMap_canContainMultipleRooms() {
        val map = mapOf(
            "r1" to "https://img.com/r1.jpg",
            "r2" to "https://img.com/r2.jpg",
            "r3" to "https://img.com/r3.jpg"
        )
        viewModel.roomImagesMap = map
        assertEquals(3, viewModel.roomImagesMap.size)
        assertEquals("https://img.com/r1.jpg", viewModel.roomImagesMap["r1"])
        assertNull(viewModel.roomImagesMap["r99"])
    }

    // === LastPayment ===

    @Test
    fun lastPayment_initiallyNull() {
        assertNull(viewModel.lastPayment)
    }

    @Test
    fun lastPayment_canBeSet() {
        val payment = PaymentResponse(
            id = "p1",
            bookingId = "b1",
            status = "confirmed",
            amount = "714.00",
            currency = "USD",
            providerTransactionId = "MOCK-TXN-12345",
            message = "Payment processed"
        )
        viewModel.lastPayment = payment
        assertNotNull(viewModel.lastPayment)
        assertEquals("p1", viewModel.lastPayment?.id)
        assertEquals("confirmed", viewModel.lastPayment?.status)
        assertEquals("MOCK-TXN-12345", viewModel.lastPayment?.providerTransactionId)
    }

    // === SelectedBookingForPayment ===

    @Test
    fun selectedBookingForPayment_initiallyNull() {
        assertNull(viewModel.selectedBookingForPayment)
    }

    @Test
    fun selectedBookingForPayment_canBeSetFromBookingList() {
        val bookings = listOf(
            BookingResponse(id = "b1", finalPrice = "500.00"),
            BookingResponse(id = "b2", finalPrice = "900.00")
        )
        viewModel.myBookings = bookings
        val toPayBooking = viewModel.myBookings.find { it.id == "b1" }
        viewModel.selectedBookingForPayment = toPayBooking
        assertEquals("b1", viewModel.selectedBookingForPayment?.id)
        assertEquals("500.00", viewModel.selectedBookingForPayment?.finalPrice)
    }

    // === IsLoading ===

    @Test
    fun isLoading_defaultFalse() {
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun isLoading_canBeSetManually() {
        viewModel.isLoading = true
        assertTrue(viewModel.isLoading)
        viewModel.isLoading = false
        assertFalse(viewModel.isLoading)
    }

    // === Error ===

    @Test
    fun error_defaultNull() {
        assertNull(viewModel.error)
    }

    @Test
    fun error_multipleUpdates() {
        viewModel.error = "First error"
        assertEquals("First error", viewModel.error)
        viewModel.error = "Second error"
        assertEquals("Second error", viewModel.error)
        viewModel.error = null
        assertNull(viewModel.error)
    }

    // === SkipNetworkForTests ===

    @Test
    fun skipNetworkForTests_defaultFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val freshViewModel = HotelViewModel(TokenManager(context))
        assertFalse(freshViewModel.skipNetworkForTests)
    }

    @Test
    fun skipNetworkForTests_canBeSetToTrue() {
        viewModel.skipNetworkForTests = true
        assertTrue(viewModel.skipNetworkForTests)
    }

    // === createBooking with no room ===

    @Test
    fun createBooking_withNoRoom_skipsAndDoesNothing() {
        viewModel.selectedRoom = null
        viewModel.createBooking("Juan", "j@t.com", "123", "456") {}
        assertNull(viewModel.lastBooking)
        assertFalse(viewModel.isLoading)
    }

    // === All network methods skip when skipNetworkForTests=true ===

    @Test
    fun allNetworkMethods_skipWhenTestMode_stateUnchanged() {
        viewModel.skipNetworkForTests = true
        viewModel.searchHotels {}
        viewModel.searchRooms {}
        viewModel.fetchBookings()
        viewModel.fetchRoomImages("r1")
        viewModel.fetchAllRoomImages(listOf("r1", "r2", "r3"))
        viewModel.createPayment("b1", 200.0, "4242", "Juan", "j@t.com") {}
        assertFalse(viewModel.isLoading)
        assertTrue(viewModel.searchResults.isEmpty())
        assertTrue(viewModel.roomResults.isEmpty())
        assertTrue(viewModel.myBookings.isEmpty())
        assertTrue(viewModel.roomImages.isEmpty())
        assertTrue(viewModel.roomImagesMap.isEmpty())
        assertNull(viewModel.lastPayment)
    }

    // === TokenManager with no token ===

    @Test
    fun viewModel_withNoToken_createSuccessfully() = runBlocking {
        tokenManager.clearToken()
        val noTokenViewModel = HotelViewModel(tokenManager)
        noTokenViewModel.skipNetworkForTests = true
        assertNull(noTokenViewModel.error)
        assertNull(noTokenViewModel.lastBooking)
        assertNull(noTokenViewModel.selectedRoom)
    }

    // === BookingResponse fields ===

    @Test
    fun bookingResponse_withQrFields_accessibleFromViewModel() {
        val booking = BookingResponse(
            id = "b1",
            bookingCode = "TH-2026-QR",
            qrCode = "iVBORw0KGgoAAAANS...",
            qrIsValid = true,
            qrGeneratedAt = "2026-05-01T12:00:00Z",
            paymentId = "p1",
            userId = "u1",
            hotelId = "h1",
            roomId = "r1"
        )
        viewModel.lastBooking = booking
        assertEquals("iVBORw0KGgoAAAANS...", viewModel.lastBooking?.qrCode)
        assertEquals(true, viewModel.lastBooking?.qrIsValid)
        assertEquals("2026-05-01T12:00:00Z", viewModel.lastBooking?.qrGeneratedAt)
    }
}
