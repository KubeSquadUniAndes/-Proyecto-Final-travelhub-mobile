package com.example.travelhubapp_mobile.network

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterRequestGetterTest {

    @Test
    fun email_getter() {
        val req = RegisterRequest(
            "Juan", "García", "juan@test.com", "+57300", "Colombia",
            "Bogotá", "1995-01-01", "pass123!", "traveler", "CC", "12345"
        )
        assertEquals("juan@test.com", req.email)
    }

    @Test
    fun phone_getter() {
        val req = RegisterRequest(
            "Ana", "López", "ana@test.com", "+57301987654", "Colombia",
            "Medellín", "2000-05-15", "MyPass123!", "traveler", "CE", "AB123"
        )
        assertEquals("+57301987654", req.phone)
    }

    @Test
    fun country_getter() {
        val req = RegisterRequest(
            "Pedro", "Rodríguez", "pedro@test.com", "+573001234567",
            "México", "Ciudad de México", "1988-12-25", "SecurePass1!",
            "traveler", "PA", "MX123456"
        )
        assertEquals("México", req.country)
    }

    @Test
    fun birthDate_getter() {
        val req = RegisterRequest(
            "María", "Santos", "maria@test.com", "+57302111222",
            "Colombia", "Cali", "1992-08-15", "Password1!",
            "traveler", "TI", "9876543"
        )
        assertEquals("1992-08-15", req.birthDate)
    }

    @Test
    fun password_getter() {
        val req = RegisterRequest(
            "Carlos", "Vega", "carlos@test.com", "+57300999888",
            "Colombia", "Barranquilla", "1985-03-20", "SuperSecret99",
            "traveler", "CC", "111222333"
        )
        assertEquals("SuperSecret99", req.password)
    }

    @Test
    fun identificationType_getter() {
        val req = RegisterRequest(
            "Laura", "Kim", "laura@test.com", "+57301888777",
            "Colombia", "Bucaramanga", "1998-07-04", "Passw0rd!",
            "traveler", "PA", "KR987654"
        )
        assertEquals("PA", req.identificationType)
    }

    @Test
    fun identificationNumber_getter() {
        val req = RegisterRequest(
            "Andrés", "Mora", "andres@test.com", "+57300777666",
            "Colombia", "Manizales", "2001-11-11", "Mypass123!",
            "traveler", "CC", "987654321"
        )
        assertEquals("987654321", req.identificationNumber)
    }

    @Test
    fun allFieldsAccessed() {
        val req = RegisterRequest(
            firstName = "Test", lastName = "User", email = "test@example.com",
            phone = "+57300000001", country = "Colombia", city = "Bogotá",
            birthDate = "1990-01-01", password = "TestPass123",
            userType = "traveler", identificationType = "CC", identificationNumber = "10000001"
        )
        assertEquals("Test", req.firstName)
        assertEquals("User", req.lastName)
        assertEquals("test@example.com", req.email)
        assertEquals("+57300000001", req.phone)
        assertEquals("Colombia", req.country)
        assertEquals("Bogotá", req.city)
        assertEquals("1990-01-01", req.birthDate)
        assertEquals("TestPass123", req.password)
        assertEquals("traveler", req.userType)
        assertEquals("CC", req.identificationType)
        assertEquals("10000001", req.identificationNumber)
    }

    @Test
    fun hashCode_consistent() {
        val r1 = RegisterRequest("J", "G", "j@t.com", "123", "Col",
            "Bog", "1990-01-01", "pass", "traveler", "CC", "111")
        val r2 = RegisterRequest("J", "G", "j@t.com", "123", "Col",
            "Bog", "1990-01-01", "pass", "traveler", "CC", "111")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun toString_containsFields() {
        val req = RegisterRequest("Juan", "García", "juan@t.com", "123", "Col",
            "Bog", "1990-01-01", "pass", "traveler", "CC", "999")
        val str = req.toString()
        assertTrue(str.contains("Juan"))
        assertTrue(str.contains("García"))
    }
}

class BookingRequestGetterTest {

    @Test
    fun roomId_getter() {
        val req = BookingRequest(
            "h1", "room-abc-123", "2026-06-01T15:00:00Z", "2026-06-05T11:00:00Z",
            "Deluxe", 2, 150.0, "Juan", "juan@test.com", "+57300", "DOC123"
        )
        assertEquals("room-abc-123", req.roomId)
    }

    @Test
    fun startTime_getter() {
        val req = BookingRequest(
            "h1", "r1", "2026-07-10T15:00:00Z", "2026-07-15T11:00:00Z",
            "Suite", 3, 300.0, "Ana", "ana@test.com", "+57301", "DOC456"
        )
        assertEquals("2026-07-10T15:00:00Z", req.startTime)
    }

    @Test
    fun endTime_getter() {
        val req = BookingRequest(
            "h1", "r1", "2026-08-01T15:00:00Z", "2026-08-07T11:00:00Z",
            "Standard", 1, 90.0, "Pedro", "pedro@test.com", "+57302", "DOC789"
        )
        assertEquals("2026-08-07T11:00:00Z", req.endTime)
    }

    @Test
    fun roomType_getter() {
        val req = BookingRequest(
            "h2", "r2", "2026-09-01T15:00:00Z", "2026-09-03T11:00:00Z",
            "Presidential Suite", 4, 500.0, "María", "maria@test.com", "+57303", "DOCABC"
        )
        assertEquals("Presidential Suite", req.roomType)
    }

    @Test
    fun pricePerNight_getter() {
        val req = BookingRequest(
            "h3", "r3", "2026-10-01T15:00:00Z", "2026-10-04T11:00:00Z",
            "Doble", 2, 185.50, "Carlos", "carlos@test.com", "+57304", "DOCDEF"
        )
        assertEquals(185.50, req.pricePerNight, 0.001)
    }

    @Test
    fun travelerEmail_getter() {
        val req = BookingRequest(
            "h1", "r1", "2026-11-01T15:00:00Z", "2026-11-05T11:00:00Z",
            "Deluxe", 2, 200.0, "Laura", "laura.special@company.org", "+57305", "DOCGHI"
        )
        assertEquals("laura.special@company.org", req.travelerEmail)
    }

    @Test
    fun travelerPhone_getter() {
        val req = BookingRequest(
            "h1", "r1", "2026-12-01T15:00:00Z", "2026-12-10T11:00:00Z",
            "Standard", 1, 100.0, "Andrés", "andres@test.com", "+57 300 999 8888", "DOCJKL"
        )
        assertEquals("+57 300 999 8888", req.travelerPhone)
    }

    @Test
    fun travelerDocument_getter() {
        val req = BookingRequest(
            "h4", "r4", "2027-01-01T15:00:00Z", "2027-01-03T11:00:00Z",
            "Single", 1, 75.0, "Sofía", "sofia@test.com", "+57306", "PASSPORT-AB123456"
        )
        assertEquals("PASSPORT-AB123456", req.travelerDocument)
    }

    @Test
    fun hashCode_consistent() {
        val r1 = BookingRequest("h1", "r1", "s", "e", "type", 2, 100.0, "N", "e@t.com", "p", "d")
        val r2 = BookingRequest("h1", "r1", "s", "e", "type", 2, 100.0, "N", "e@t.com", "p", "d")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun toString_containsFields() {
        val req = BookingRequest("hotel1", "room1", "start", "end", "Deluxe", 2, 200.0,
            "TestUser", "test@test.com", "+57300", "DOC001")
        val str = req.toString()
        assertTrue(str.contains("hotel1"))
        assertTrue(str.contains("TestUser"))
    }
}

class BookingResponseGetterTest {

    @Test
    fun userId_getter() {
        val res = BookingResponse(id = "b1", userId = "user-uuid-001", status = "confirmed")
        assertEquals("user-uuid-001", res.userId)
    }

    @Test
    fun hotelId_getter() {
        val res = BookingResponse(id = "b1", hotelId = "hotel-uuid-001", roomId = "room-uuid-001")
        assertEquals("hotel-uuid-001", res.hotelId)
    }

    @Test
    fun notes_getter() {
        val res = BookingResponse(id = "b1", notes = "Special dietary requirements")
        assertEquals("Special dietary requirements", res.notes)
    }

    @Test
    fun notes_nullByDefault() {
        val res = BookingResponse(id = "b1")
        assertNull(res.notes)
    }

    @Test
    fun additionalGuests_getter() {
        val res = BookingResponse(id = "b1", additionalGuests = listOf("Guest A", "Guest B", "Guest C"))
        assertEquals(3, res.additionalGuests?.size)
        assertEquals("Guest A", res.additionalGuests?.get(0))
    }

    @Test
    fun additionalGuests_nullDefault() {
        val res = BookingResponse(id = "b1")
        assertNull(res.additionalGuests)
    }

    @Test
    fun specialRequests_getter() {
        val res = BookingResponse(id = "b1", specialRequests = "Early check-in, ground floor")
        assertEquals("Early check-in, ground floor", res.specialRequests)
    }

    @Test
    fun travelerDocument_getter() {
        val res = BookingResponse(id = "b1", travelerDocument = "CC-98765432")
        assertEquals("CC-98765432", res.travelerDocument)
    }

    @Test
    fun updatedAt_getter() {
        val res = BookingResponse(id = "b1", updatedAt = "2026-05-15T09:30:00Z")
        assertEquals("2026-05-15T09:30:00Z", res.updatedAt)
    }

    @Test
    fun message_getter() {
        val res = BookingResponse(id = "b1", message = "Booking confirmed successfully")
        assertEquals("Booking confirmed successfully", res.message)
    }

    @Test
    fun allFieldsAccessed() {
        val res = BookingResponse(
            id = "full-booking-001", userId = "user-001", hotelId = "hotel-001",
            roomId = "room-001", startTime = "2026-06-01T15:00:00Z",
            endTime = "2026-06-07T11:00:00Z", status = "confirmed",
            notes = "Non-smoking room", bookingCode = "TH-2026-001", roomType = "Deluxe",
            numGuests = 2, additionalGuests = listOf("Extra Guest"),
            specialRequests = "Late check-out", pricePerNight = "200.00",
            totalNights = 6, totalPrice = "1200.00", taxes = "216.00",
            finalPrice = "1416.00", travelerName = "Juan García",
            travelerEmail = "juan@test.com", travelerPhone = "+57300123456",
            travelerDocument = "CC-12345678", cancellable = true, paymentId = "pay-001",
            createdAt = "2026-05-01T10:00:00Z", updatedAt = "2026-05-01T10:05:00Z",
            message = "Success"
        )
        assertEquals("full-booking-001", res.id)
        assertEquals("Non-smoking room", res.notes)
        assertEquals("Late check-out", res.specialRequests)
        assertEquals("CC-12345678", res.travelerDocument)
        assertEquals("2026-05-01T10:05:00Z", res.updatedAt)
        assertEquals("Success", res.message)
    }

    @Test
    fun copy_withMissingFields() {
        val original = BookingResponse(
            id = "b1", userId = "u1", notes = "Original note",
            additionalGuests = listOf("Guest1"), message = "Original message",
            updatedAt = "2026-01-01T00:00:00Z"
        )
        val copy = original.copy(notes = "Updated note", message = "Updated message")
        assertEquals("Updated note", copy.notes)
        assertEquals("Updated message", copy.message)
        assertEquals("u1", copy.userId)
    }

    @Test
    fun hashCode_consistent() {
        val r1 = BookingResponse(id = "b1", bookingCode = "TH-001", status = "confirmed")
        val r2 = BookingResponse(id = "b1", bookingCode = "TH-001", status = "confirmed")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun toString_containsId() {
        val res = BookingResponse(id = "booking-unique-id", bookingCode = "TH-UNIQUE")
        assertTrue(res.toString().contains("booking-unique-id"))
    }
}

class RoomHotelGetterTest {

    @Test
    fun roomResponse_allGetters() {
        val room = RoomResponse(
            id = "r-full-001", hotelId = "h-full-001", hotelName = "Grand Palace Hotel",
            destination = "Cartagena", name = "Ocean View Suite", roomType = "suite",
            price = "450000", capacity = 3, beds = "1 king + 1 twin", size = 65.0,
            status = "disponible", amenities = "WiFi,AC,TV", createdAt = "2025-01-15T08:00:00Z",
            updatedAt = "2025-06-01T10:00:00Z"
        )
        assertEquals("r-full-001", room.id)
        assertEquals("Grand Palace Hotel", room.hotelName)
        assertEquals("Cartagena", room.destination)
        assertEquals("suite", room.roomType)
        assertEquals(3, room.capacity)
        assertEquals(65.0, room.size)
        assertEquals("disponible", room.status)
        assertEquals("2025-01-15T08:00:00Z", room.createdAt)
        assertEquals("2025-06-01T10:00:00Z", room.updatedAt)
    }

    @Test
    fun roomResponse_copy() {
        val original = RoomResponse(
            "r1", "h1", "Hotel Test", "Bogotá", "Standard", "doble",
            "150000", 2, "1 queen", 30.0, "disponible", "WiFi", null, null
        )
        val copy = original.copy(status = "reservado", price = "180000")
        assertEquals("reservado", copy.status)
        assertEquals("180000", copy.price)
        assertEquals("r1", copy.id)
    }

    @Test
    fun roomResponse_hashCode_consistent() {
        val r1 = RoomResponse("r1", "h1", "H", "D", "N", "t", "100", 2, null, null, null, null, null, null)
        val r2 = RoomResponse("r1", "h1", "H", "D", "N", "t", "100", 2, null, null, null, null, null, null)
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun hotelSearchRequest_allGetters() {
        val req = HotelSearchRequest(
            destination = "Santa Marta", checkIn = "2026-07-15T15:00:00Z",
            checkOut = "2026-07-20T11:00:00Z", guests = 5
        )
        assertEquals("Santa Marta", req.destination)
        assertEquals("2026-07-15T15:00:00Z", req.checkIn)
        assertEquals("2026-07-20T11:00:00Z", req.checkOut)
        assertEquals(5, req.guests)
    }

    @Test
    fun hotelResponse_allGetters() {
        val res = HotelResponse(
            id = "hotel-001", name = "Luxury Palace", rating = "4.8",
            price = "350000", reviews = "523", location = "Cartagena, Colombia",
            image = "https://images.example.com/hotel-001.jpg"
        )
        assertEquals("hotel-001", res.id)
        assertEquals("Luxury Palace", res.name)
        assertEquals("4.8", res.rating)
        assertEquals("350000", res.price)
        assertEquals("523", res.reviews)
        assertEquals("Cartagena, Colombia", res.location)
        assertEquals("https://images.example.com/hotel-001.jpg", res.image)
    }

    @Test
    fun hotelResponse_hashCode_consistent() {
        val r1 = HotelResponse("h1", "Hotel", "4.0", "100000", null, null, null)
        val r2 = HotelResponse("h1", "Hotel", "4.0", "100000", null, null, null)
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun roomImageResponse_allGetters() {
        val res = RoomImageResponse(
            id = "img-001", roomId = "room-001",
            url = "https://cdn.example.com/rooms/001/image1.jpg",
            createdAt = "2025-12-01T08:00:00Z"
        )
        assertEquals("img-001", res.id)
        assertEquals("room-001", res.roomId)
        assertEquals("https://cdn.example.com/rooms/001/image1.jpg", res.url)
        assertEquals("2025-12-01T08:00:00Z", res.createdAt)
    }

    @Test
    fun roomImageResponse_hashCode_consistent() {
        val r1 = RoomImageResponse("img1", "r1", "https://url.com/img.jpg", null)
        val r2 = RoomImageResponse("img1", "r1", "https://url.com/img.jpg", null)
        assertEquals(r1.hashCode(), r2.hashCode())
    }
}

class PaymentAndAuthGetterTest {

    @Test
    fun paymentRequest_allGetters() {
        val req = PaymentRequest(
            bookingId = "booking-xyz", amount = 714.00, currency = "USD",
            paymentProvider = "stripe", paymentMethod = "debit_card",
            cardLastFour = "5678", cardholderName = "María García",
            cardholderEmail = "maria.garcia@company.co"
        )
        assertEquals("booking-xyz", req.bookingId)
        assertEquals(714.00, req.amount, 0.001)
        assertEquals("USD", req.currency)
        assertEquals("stripe", req.paymentProvider)
        assertEquals("debit_card", req.paymentMethod)
        assertEquals("5678", req.cardLastFour)
        assertEquals("María García", req.cardholderName)
        assertEquals("maria.garcia@company.co", req.cardholderEmail)
    }

    @Test
    fun paymentRequest_defaultCurrencyAndProvider() {
        val req = PaymentRequest(
            bookingId = "bk1", amount = 100.0,
            cardLastFour = "1234", cardholderName = "Test User",
            cardholderEmail = "test@test.com"
        )
        assertEquals("USD", req.currency)
        assertEquals("mock", req.paymentProvider)
        assertEquals("credit_card", req.paymentMethod)
    }

    @Test
    fun paymentRequest_hashCode_consistent() {
        val r1 = PaymentRequest("b1", 100.0, "USD", "mock", "credit_card", "4242", "Juan", "j@t.com")
        val r2 = PaymentRequest("b1", 100.0, "USD", "mock", "credit_card", "4242", "Juan", "j@t.com")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun paymentResponse_allGetters() {
        val res = PaymentResponse(
            id = "pay-001", bookingId = "book-001", status = "confirmed",
            amount = "714.00", currency = "USD",
            providerTransactionId = "MOCK-TXN-12345678",
            message = "Payment processed successfully"
        )
        assertEquals("pay-001", res.id)
        assertEquals("book-001", res.bookingId)
        assertEquals("confirmed", res.status)
        assertEquals("714.00", res.amount)
        assertEquals("USD", res.currency)
        assertEquals("MOCK-TXN-12345678", res.providerTransactionId)
        assertEquals("Payment processed successfully", res.message)
    }

    @Test
    fun paymentResponse_hashCode_consistent() {
        val r1 = PaymentResponse("p1", "b1", "confirmed", "100", "USD", "TXN1", "ok")
        val r2 = PaymentResponse("p1", "b1", "confirmed", "100", "USD", "TXN1", "ok")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun paymentConfirmRequest_allGetters() {
        val req = PaymentConfirmRequest(
            providerTransactionId = "MOCK-TXN-1748012345678",
            paymentTimestamp = "2026-05-16T10:30:00Z"
        )
        assertEquals("MOCK-TXN-1748012345678", req.providerTransactionId)
        assertEquals("2026-05-16T10:30:00Z", req.paymentTimestamp)
    }

    @Test
    fun paymentConfirmRequest_hashCode_consistent() {
        val r1 = PaymentConfirmRequest("TXN-1", "2026-01-01T00:00:00Z")
        val r2 = PaymentConfirmRequest("TXN-1", "2026-01-01T00:00:00Z")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun fcmTokenRequest_allGetters() {
        val req = FcmTokenRequest(fcmToken = "firebase-token-abc123xyz", platform = "android")
        assertEquals("firebase-token-abc123xyz", req.fcmToken)
        assertEquals("android", req.platform)
    }

    @Test
    fun fcmTokenRequest_defaultPlatform() {
        val req = FcmTokenRequest("token-abc")
        assertEquals("android", req.platform)
    }

    @Test
    fun fcmTokenRequest_hashCode_consistent() {
        val r1 = FcmTokenRequest("token", "android")
        val r2 = FcmTokenRequest("token", "android")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun loginRequest_allGetters() {
        val req = LoginRequest(email = "traveler@gmail.com", password = "MySecurePass123!")
        assertEquals("traveler@gmail.com", req.email)
        assertEquals("MySecurePass123!", req.password)
    }

    @Test
    fun loginRequest_hashCode_consistent() {
        val r1 = LoginRequest("a@b.com", "pass")
        val r2 = LoginRequest("a@b.com", "pass")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun loginResponse_allGetters() {
        val res = LoginResponse(
            accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
            tokenType = "bearer", message = "Login successful", detail = null
        )
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", res.accessToken)
        assertEquals("bearer", res.tokenType)
        assertEquals("Login successful", res.message)
        assertNull(res.detail)
    }

    @Test
    fun loginResponse_errorDetail() {
        val res = LoginResponse(accessToken = null, tokenType = null, message = null, detail = "Invalid credentials")
        assertNull(res.accessToken)
        assertEquals("Invalid credentials", res.detail)
    }

    @Test
    fun loginResponse_hashCode_consistent() {
        val r1 = LoginResponse("tok", "bearer", null, null)
        val r2 = LoginResponse("tok", "bearer", null, null)
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun registerResponse_allGetters() {
        val res = RegisterResponse(id = "new-user-001", message = "User created", email = "new@test.com")
        assertEquals("new-user-001", res.id)
        assertEquals("User created", res.message)
        assertEquals("new@test.com", res.email)
    }

    @Test
    fun registerResponse_hashCode_consistent() {
        val r1 = RegisterResponse("id1", "Created", "t@t.com")
        val r2 = RegisterResponse("id1", "Created", "t@t.com")
        assertEquals(r1.hashCode(), r2.hashCode())
    }

    @Test
    fun profileResponse_allGetters() {
        val res = ProfileResponse(
            id = "prof-001", firstName = "Valentina", lastName = "Moreno",
            fullName = "Valentina Moreno", email = "valentina@travelhub.com",
            phone = "+57 310 555 9999", userType = "traveler", country = "Colombia",
            city = "Cali", status = "active", role = "traveler"
        )
        assertEquals("prof-001", res.id)
        assertEquals("Valentina", res.firstName)
        assertEquals("Moreno", res.lastName)
        assertEquals("Valentina Moreno", res.fullName)
        assertEquals("traveler", res.userType)
        assertEquals("active", res.status)
    }

    @Test
    fun profileResponse_hashCode_consistent() {
        val r1 = ProfileResponse("id1", "Juan", null, null, "j@t.com", null, "traveler", null, null, "active", "traveler")
        val r2 = ProfileResponse("id1", "Juan", null, null, "j@t.com", null, "traveler", null, null, "active", "traveler")
        assertEquals(r1.hashCode(), r2.hashCode())
    }
}
