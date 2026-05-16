package com.example.travelhubapp_mobile.network

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelsExtendedTest {

    private val gson = Gson()

    // === HotelSearchRequest ===

    @Test
    fun hotelSearchRequest_defaultGuests_isTwo() {
        val request = HotelSearchRequest(
            destination = "Bogotá",
            checkIn = "2026-06-01T15:00:00Z",
            checkOut = "2026-06-05T11:00:00Z"
        )
        assertEquals(2, request.guests)
    }

    @Test
    fun hotelSearchRequest_customGuests() {
        val request = HotelSearchRequest(
            destination = "Cartagena",
            checkIn = "2026-07-01T15:00:00Z",
            checkOut = "2026-07-05T11:00:00Z",
            guests = 4
        )
        assertEquals(4, request.guests)
        assertEquals("Cartagena", request.destination)
    }

    @Test
    fun hotelSearchRequest_serializesCorrectly() {
        val request = HotelSearchRequest(
            destination = "Medellín",
            checkIn = "2026-08-01",
            checkOut = "2026-08-05",
            guests = 3
        )
        val json = gson.toJson(request)
        assertTrue(json.contains("\"destination\":\"Medellín\""))
        assertTrue(json.contains("\"checkIn\":\"2026-08-01\""))
        assertTrue(json.contains("\"checkOut\":\"2026-08-05\""))
        assertTrue(json.contains("\"guests\":3"))
    }

    @Test
    fun hotelSearchRequest_equality() {
        val r1 = HotelSearchRequest("Bogotá", "2026-06-01", "2026-06-05", 2)
        val r2 = HotelSearchRequest("Bogotá", "2026-06-01", "2026-06-05", 2)
        assertEquals(r1, r2)
    }

    @Test
    fun hotelSearchRequest_copyWorks() {
        val original = HotelSearchRequest("Bogotá", "2026-06-01", "2026-06-05", 2)
        val copy = original.copy(destination = "Cali", guests = 6)
        assertEquals("Cali", copy.destination)
        assertEquals(6, copy.guests)
        assertEquals("2026-06-01", copy.checkIn)
    }

    // === BookingRequest ===

    @Test
    fun bookingRequest_defaultNotes_andSpecialRequests() {
        val request = BookingRequest(
            hotelId = "h1",
            roomId = "r1",
            startTime = "2026-06-01T15:00:00Z",
            endTime = "2026-06-05T11:00:00Z",
            roomType = "Deluxe",
            numGuests = 2,
            pricePerNight = 150.0,
            travelerName = "Juan",
            travelerEmail = "juan@test.com",
            travelerPhone = "+57 300 123 4567",
            travelerDocument = "1234567890"
        )
        assertEquals("Test booking", request.notes)
        assertEquals("Late check-in", request.specialRequests)
    }

    @Test
    fun bookingRequest_customNotes_andSpecialRequests() {
        val request = BookingRequest(
            hotelId = "h1",
            roomId = "r1",
            startTime = "2026-06-01T15:00:00Z",
            endTime = "2026-06-05T11:00:00Z",
            roomType = "Suite",
            numGuests = 3,
            pricePerNight = 300.0,
            travelerName = "Ana García",
            travelerEmail = "ana@test.com",
            travelerPhone = "+57 301 987 6543",
            travelerDocument = "9876543210",
            notes = "Custom note",
            specialRequests = "Early check-in"
        )
        assertEquals("Custom note", request.notes)
        assertEquals("Early check-in", request.specialRequests)
    }

    @Test
    fun bookingRequest_serializesCorrectly() {
        val request = BookingRequest(
            hotelId = "h1",
            roomId = "r1",
            startTime = "2026-06-01T15:00:00Z",
            endTime = "2026-06-05T11:00:00Z",
            roomType = "Deluxe",
            numGuests = 2,
            pricePerNight = 150.0,
            travelerName = "Juan",
            travelerEmail = "juan@test.com",
            travelerPhone = "+57 300 123 4567",
            travelerDocument = "1234567890"
        )
        val json = gson.toJson(request)
        assertTrue(json.contains("\"hotel_id\":\"h1\""))
        assertTrue(json.contains("\"room_id\":\"r1\""))
        assertTrue(json.contains("\"traveler_name\":\"Juan\""))
        assertTrue(json.contains("\"traveler_email\":\"juan@test.com\""))
        assertTrue(json.contains("\"num_guests\":2"))
        assertTrue(json.contains("\"price_per_night\":150.0"))
    }

    @Test
    fun bookingRequest_equality() {
        val r1 = BookingRequest(
            "h1", "r1", "2026-06-01", "2026-06-05",
            "Deluxe", 2, 150.0, "Juan", "j@t.com", "123", "456"
        )
        val r2 = BookingRequest(
            "h1", "r1", "2026-06-01", "2026-06-05",
            "Deluxe", 2, 150.0, "Juan", "j@t.com", "123", "456"
        )
        assertEquals(r1, r2)
    }

    @Test
    fun bookingRequest_copyWorks() {
        val original = BookingRequest(
            "h1", "r1", "2026-06-01", "2026-06-05",
            "Deluxe", 2, 150.0, "Juan", "j@t.com", "123", "456"
        )
        val copy = original.copy(numGuests = 4, travelerName = "María")
        assertEquals(4, copy.numGuests)
        assertEquals("María", copy.travelerName)
        assertEquals("h1", copy.hotelId)
    }

    // === HotelResponse ===

    @Test
    fun hotelResponse_deserializesCorrectly() {
        val json = """{"id":"h1","name":"Grand Hotel","rating":"4.5","price":"200000",""" +
            """"reviews":"150","location":"Bogotá","image":"https://img.com/hotel.jpg"}"""
        val response = gson.fromJson(json, HotelResponse::class.java)
        assertEquals("h1", response.id)
        assertEquals("Grand Hotel", response.name)
        assertEquals("4.5", response.rating)
        assertEquals("200000", response.price)
        assertEquals("150", response.reviews)
        assertEquals("Bogotá", response.location)
        assertEquals("https://img.com/hotel.jpg", response.image)
    }

    @Test
    fun hotelResponse_withNullOptionalFields() {
        val json = """{"id":"h1","name":"Basic Hotel","rating":"3.0","price":"100000"}"""
        val response = gson.fromJson(json, HotelResponse::class.java)
        assertNull(response.reviews)
        assertNull(response.location)
        assertNull(response.image)
    }

    @Test
    fun hotelResponse_equality() {
        val r1 = HotelResponse("h1", "Hotel A", "4.5", "200000", "100", "Bogotá", null)
        val r2 = HotelResponse("h1", "Hotel A", "4.5", "200000", "100", "Bogotá", null)
        assertEquals(r1, r2)
    }

    // === RoomResponse ===

    @Test
    fun roomResponse_deserializesCorrectly() {
        val json = """{"id":"r1","hotel_id":"h1","hotel_name":"Grand Hotel","destination":"Bogotá",
            "name":"Suite Deluxe","room_type":"doble","price":"300000","capacity":4,
            "beds":"2 camas","size":45.0,"status":"disponible","amenities":"WiFi,AC"}"""
        val response = gson.fromJson(json, RoomResponse::class.java)
        assertEquals("r1", response.id)
        assertEquals("h1", response.hotelId)
        assertEquals("Grand Hotel", response.hotelName)
        assertEquals(4, response.capacity)
        assertEquals(45.0, response.size)
        assertEquals("WiFi,AC", response.amenities)
    }

    // === ProfileResponse extended ===

    @Test
    fun profileResponse_withAllFields() {
        val json = """{"id":"123","first_name":"Juan","last_name":"García","full_name":"Juan García",
            "email":"juan@test.com","phone":"+57 300 123 4567","user_type":"traveler",
            "country":"Colombia","city":"Bogotá","status":"active","role":"traveler"}"""
        val response = gson.fromJson(json, ProfileResponse::class.java)
        assertEquals("123", response.id)
        assertEquals("Juan", response.firstName)
        assertEquals("García", response.lastName)
        assertEquals("Juan García", response.fullName)
        assertEquals("juan@test.com", response.email)
        assertEquals("+57 300 123 4567", response.phone)
        assertEquals("traveler", response.userType)
        assertEquals("Colombia", response.country)
        assertEquals("Bogotá", response.city)
        assertEquals("active", response.status)
        assertEquals("traveler", response.role)
    }

    @Test
    fun profileResponse_withHotelRole() {
        val json = """{"id":"456","email":"hotel@test.com","role":"hotel","user_type":"hotel_owner"}"""
        val response = gson.fromJson(json, ProfileResponse::class.java)
        assertEquals("hotel", response.role)
        assertEquals("hotel_owner", response.userType)
    }

    @Test
    fun profileResponse_equality() {
        val r1 = ProfileResponse("id1", "Juan", "García", "Juan García", "j@t.com",
            "+57300", "traveler", "Colombia", "Bogotá", "active", "traveler")
        val r2 = ProfileResponse("id1", "Juan", "García", "Juan García", "j@t.com",
            "+57300", "traveler", "Colombia", "Bogotá", "active", "traveler")
        assertEquals(r1, r2)
    }

    @Test
    fun profileResponse_copyWorks() {
        val original = ProfileResponse("id1", "Juan", "García", null, "j@t.com",
            null, "traveler", null, null, "active", "traveler")
        val copy = original.copy(city = "Medellín", status = "inactive")
        assertEquals("Medellín", copy.city)
        assertEquals("inactive", copy.status)
        assertEquals("Juan", copy.firstName)
    }

    // === LoginResponse extended ===

    @Test
    fun loginResponse_withMessage() {
        val json = """{"message":"Login successful","access_token":"tok123","token_type":"bearer"}"""
        val response = gson.fromJson(json, LoginResponse::class.java)
        assertEquals("tok123", response.accessToken)
        assertEquals("Login successful", response.message)
        assertEquals("bearer", response.tokenType)
    }

    @Test
    fun loginResponse_equality() {
        val r1 = LoginResponse("tok123", "bearer", null, null)
        val r2 = LoginResponse("tok123", "bearer", null, null)
        assertEquals(r1, r2)
    }

    @Test
    fun loginResponse_copyWorks() {
        val original = LoginResponse("tok123", "bearer", null, null)
        val copy = original.copy(tokenType = "JWT")
        assertEquals("JWT", copy.tokenType)
        assertEquals("tok123", copy.accessToken)
    }

    // === RegisterResponse extended ===

    @Test
    fun registerResponse_equality() {
        val r1 = RegisterResponse("id1", "Created", "t@t.com")
        val r2 = RegisterResponse("id1", "Created", "t@t.com")
        assertEquals(r1, r2)
    }

    @Test
    fun registerResponse_copyWorks() {
        val original = RegisterResponse("id1", "Created", "t@t.com")
        val copy = original.copy(message = "Updated")
        assertEquals("Updated", copy.message)
        assertEquals("id1", copy.id)
    }

    @Test
    fun registerResponse_withNullFields() {
        val json = """{}"""
        val response = gson.fromJson(json, RegisterResponse::class.java)
        assertNull(response.id)
        assertNull(response.message)
        assertNull(response.email)
    }

    // === BookingResponse extended ===

    @Test
    fun bookingResponse_withAllTravelerFields() {
        val json = """{"id":"b1","booking_code":"TH-001","traveler_name":"Juan",
            "traveler_email":"j@t.com","traveler_phone":"+57300","traveler_document":"123",
            "num_guests":2,"room_type":"Deluxe","price_per_night":"150.00",
            "total_nights":4,"total_price":"600.00","taxes":"108.00","final_price":"708.00"}"""
        val response = gson.fromJson(json, BookingResponse::class.java)
        assertEquals("Juan", response.travelerName)
        assertEquals("j@t.com", response.travelerEmail)
        assertEquals("TH-001", response.bookingCode)
        assertEquals(2, response.numGuests)
        assertEquals("708.00", response.finalPrice)
        assertEquals(4, response.totalNights)
        assertEquals("108.00", response.taxes)
    }

    @Test
    fun bookingResponse_statusDisplay() {
        val json = """{"id":"b1","status":"confirmed","status_display":"Confirmada"}"""
        val response = gson.fromJson(json, BookingResponse::class.java)
        assertEquals("confirmed", response.status)
        assertEquals("Confirmada", response.statusDisplay)
    }

    @Test
    fun bookingResponse_withCancellable() {
        val json = """{"id":"b1","cancellable":true,"payment_id":"p1"}"""
        val response = gson.fromJson(json, BookingResponse::class.java)
        assertEquals(true, response.cancellable)
        assertEquals("p1", response.paymentId)
    }

    @Test
    fun bookingResponse_withDates() {
        val json = """{"id":"b1","start_time":"2026-06-01T15:00:00Z","end_time":"2026-06-05T11:00:00Z",
            "created_at":"2026-05-01T10:00:00Z","updated_at":"2026-05-02T09:00:00Z"}"""
        val response = gson.fromJson(json, BookingResponse::class.java)
        assertEquals("2026-06-01T15:00:00Z", response.startTime)
        assertEquals("2026-06-05T11:00:00Z", response.endTime)
        assertEquals("2026-05-01T10:00:00Z", response.createdAt)
    }

    // === PaymentResponse extended ===

    @Test
    fun paymentResponse_withAllFields() {
        val json = """{"id":"p1","booking_id":"b1","status":"confirmed","amount":"714.00",
            "currency":"USD","provider_transaction_id":"MOCK-TXN-123","message":"Payment confirmed"}"""
        val response = gson.fromJson(json, PaymentResponse::class.java)
        assertEquals("p1", response.id)
        assertEquals("b1", response.bookingId)
        assertEquals("confirmed", response.status)
        assertEquals("714.00", response.amount)
        assertEquals("USD", response.currency)
        assertEquals("MOCK-TXN-123", response.providerTransactionId)
        assertEquals("Payment confirmed", response.message)
    }

    @Test
    fun paymentResponse_emptyDefaults() {
        val response = PaymentResponse()
        assertNull(response.id)
        assertNull(response.bookingId)
        assertNull(response.status)
    }

    @Test
    fun paymentResponse_equality() {
        val r1 = PaymentResponse("p1", "b1", "confirmed", "100.0", "USD", "TXN-1", "ok")
        val r2 = PaymentResponse("p1", "b1", "confirmed", "100.0", "USD", "TXN-1", "ok")
        assertEquals(r1, r2)
    }

    @Test
    fun paymentResponse_copyWorks() {
        val original = PaymentResponse("p1", "b1", "pending", "100.0", "USD", null, null)
        val copy = original.copy(status = "confirmed", message = "Done")
        assertEquals("confirmed", copy.status)
        assertEquals("Done", copy.message)
        assertEquals("p1", copy.id)
    }

    // === FcmTokenRequest ===

    @Test
    fun fcmTokenRequest_customPlatform() {
        val request = FcmTokenRequest(fcmToken = "tok123", platform = "ios")
        assertEquals("ios", request.platform)
    }

    @Test
    fun fcmTokenRequest_equality() {
        val r1 = FcmTokenRequest("tok123", "android")
        val r2 = FcmTokenRequest("tok123", "android")
        assertEquals(r1, r2)
    }

    @Test
    fun fcmTokenRequest_copyWorks() {
        val original = FcmTokenRequest("tok123")
        val copy = original.copy(fcmToken = "tok456")
        assertEquals("tok456", copy.fcmToken)
        assertEquals("android", copy.platform)
    }

    // === PaymentConfirmRequest extended ===

    @Test
    fun paymentConfirmRequest_equality() {
        val r1 = PaymentConfirmRequest("TXN-1", "2026-05-01T12:00:00Z")
        val r2 = PaymentConfirmRequest("TXN-1", "2026-05-01T12:00:00Z")
        assertEquals(r1, r2)
    }

    @Test
    fun paymentConfirmRequest_copyWorks() {
        val original = PaymentConfirmRequest("TXN-1", "2026-05-01T12:00:00Z")
        val copy = original.copy(paymentTimestamp = "2026-05-02T09:00:00Z")
        assertEquals("2026-05-02T09:00:00Z", copy.paymentTimestamp)
        assertEquals("TXN-1", copy.providerTransactionId)
    }

    // === RegisterRequest extended ===

    @Test
    fun registerRequest_equality() {
        val r1 = RegisterRequest("Juan", "García", "j@t.com", "+57300", "Colombia",
            "Bogotá", "1995-01-01", "pass123", "traveler", "CC", "123")
        val r2 = RegisterRequest("Juan", "García", "j@t.com", "+57300", "Colombia",
            "Bogotá", "1995-01-01", "pass123", "traveler", "CC", "123")
        assertEquals(r1, r2)
    }

    @Test
    fun registerRequest_copyWorks() {
        val original = RegisterRequest("Juan", "García", "j@t.com", "+57300", "Colombia",
            "Bogotá", "1995-01-01", "pass123", "traveler", "CC", "123")
        val copy = original.copy(city = "Medellín", firstName = "Carlos")
        assertEquals("Medellín", copy.city)
        assertEquals("Carlos", copy.firstName)
        assertEquals("García", copy.lastName)
    }

    @Test
    fun registerRequest_allFields_serializedCorrectly() {
        val request = RegisterRequest(
            firstName = "Ana", lastName = "López",
            email = "ana@test.com", phone = "+57 301 987 6543",
            country = "Colombia", city = "Medellín",
            birthDate = "2000-05-15", password = "MyPass123!",
            identificationType = "Pasaporte",
            identificationNumber = "AB123456"
        )
        val json = gson.toJson(request)
        assertTrue(json.contains("\"identification_type\":\"Pasaporte\""))
        assertTrue(json.contains("\"identification_number\":\"AB123456\""))
        assertTrue(json.contains("\"user_type\":\"traveler\""))
        assertEquals("traveler", request.userType)
    }

    // === RoomImageResponse extended ===

    @Test
    fun roomImageResponse_equality() {
        val r1 = RoomImageResponse("img1", "r1", "https://img.com/1.jpg", "2026-01-01")
        val r2 = RoomImageResponse("img1", "r1", "https://img.com/1.jpg", "2026-01-01")
        assertEquals(r1, r2)
    }

    @Test
    fun roomImageResponse_copyWorks() {
        val original = RoomImageResponse("img1", "r1", "https://img.com/1.jpg", null)
        val copy = original.copy(url = "https://img.com/2.jpg")
        assertEquals("https://img.com/2.jpg", copy.url)
        assertEquals("img1", copy.id)
    }

    @Test
    fun roomImageResponse_withNullCreatedAt() {
        val json = """{"id":"img1","room_id":"r1","url":"https://img.com/img.jpg"}"""
        val response = gson.fromJson(json, RoomImageResponse::class.java)
        assertNull(response.createdAt)
        assertNotNull(response.url)
    }

    // === LoginRequest ===

    @Test
    fun loginRequest_equality() {
        val r1 = LoginRequest("user@test.com", "pass123")
        val r2 = LoginRequest("user@test.com", "pass123")
        assertEquals(r1, r2)
    }

    @Test
    fun loginRequest_copyWorks() {
        val original = LoginRequest("user@test.com", "pass123")
        val copy = original.copy(password = "newPass456")
        assertEquals("newPass456", copy.password)
        assertEquals("user@test.com", copy.email)
    }

    // === PaymentRequest extended ===

    @Test
    fun paymentRequest_equality() {
        val r1 = PaymentRequest("b1", 100.0, "USD", "mock", "credit_card", "4242", "Juan", "j@t.com")
        val r2 = PaymentRequest("b1", 100.0, "USD", "mock", "credit_card", "4242", "Juan", "j@t.com")
        assertEquals(r1, r2)
    }

    @Test
    fun paymentRequest_copyWorks() {
        val original = PaymentRequest("b1", 100.0, cardLastFour = "4242",
            cardholderName = "Juan", cardholderEmail = "j@t.com")
        val copy = original.copy(amount = 200.0, cardLastFour = "1234")
        assertEquals(200.0, copy.amount, 0.0)
        assertEquals("1234", copy.cardLastFour)
        assertEquals("b1", copy.bookingId)
    }
}
