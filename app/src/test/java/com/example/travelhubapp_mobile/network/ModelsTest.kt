package com.example.travelhubapp_mobile.network

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelsTest {

    private val gson = Gson()

    @Test
    fun registerRequest_serializesCorrectly() {
        val request = RegisterRequest(
            firstName = "Juan", lastName = "Pérez",
            email = "juan@test.com", phone = "+57 300 123 4567",
            password = "SecurePass123!", identificationNumber = "1234567890",
            country = "Colombia", city = "Bogotá",
            birthDate = "1995-06-15", identificationType = "CC"
        )
        val json = gson.toJson(request)
        assertTrue(json.contains("\"first_name\":\"Juan\""))
        assertTrue(json.contains("\"last_name\":\"Pérez\""))
        assertTrue(json.contains("\"identification_number\":\"1234567890\""))
        assertTrue(json.contains("\"country\":\"Colombia\""))
        assertTrue(json.contains("\"birth_date\":\"1995-06-15\""))
    }

    @Test
    fun registerRequest_hasDefaultUserType() {
        val request = RegisterRequest(
            firstName = "Test", lastName = "User",
            email = "t@t.com", phone = "123", password = "pass",
            country = "Colombia", city = "Bogotá",
            birthDate = "2000-01-01", identificationType = "CC",
            identificationNumber = "999"
        )
        assertEquals("traveler", request.userType)
    }

    @Test
    fun loginRequest_serializesCorrectly() {
        val request = LoginRequest(email = "test@mail.com", password = "pass123")
        val json = gson.toJson(request)
        assertTrue(json.contains("\"email\":\"test@mail.com\""))
        assertTrue(json.contains("\"password\":\"pass123\""))
    }

    @Test
    fun loginResponse_deserializesWithToken() {
        val json = """{"access_token":"abc123","token_type":"bearer"}"""
        val response = gson.fromJson(json, LoginResponse::class.java)
        assertEquals("abc123", response.accessToken)
        assertEquals("bearer", response.tokenType)
    }

    @Test
    fun loginResponse_deserializesWithError() {
        val json = """{"detail":"Invalid credentials"}"""
        val response = gson.fromJson(json, LoginResponse::class.java)
        assertNull(response.accessToken)
        assertEquals("Invalid credentials", response.detail)
    }

    @Test
    fun profileResponse_deserializesWithFullName() {
        val json = """{"id":"123","email":"t@m.com","full_name":"Juan","status":"active"}"""
        val response = gson.fromJson(json, ProfileResponse::class.java)
        assertEquals("123", response.id)
        assertEquals("Juan", response.fullName)
        assertEquals("active", response.status)
        assertNull(response.firstName)
    }

    @Test
    fun profileResponse_deserializesWithFirstLastName() {
        val json = """{"id":"456","first_name":"Ana","last_name":"García"}"""
        val response = gson.fromJson(json, ProfileResponse::class.java)
        assertEquals("Ana", response.firstName)
        assertEquals("García", response.lastName)
        assertNull(response.fullName)
    }

    @Test
    fun registerResponse_deserializesCorrectly() {
        val json = """{"id":"uuid-123","message":"Created","email":"t@m.com"}"""
        val response = gson.fromJson(json, RegisterResponse::class.java)
        assertEquals("uuid-123", response.id)
        assertEquals("Created", response.message)
    }

    @Test
    fun paymentRequest_serializesCorrectly() {
        val request = PaymentRequest(
            bookingId = "b1", amount = 714.0,
            cardLastFour = "4242", cardholderName = "Juan",
            cardholderEmail = "juan@test.com"
        )
        val json = gson.toJson(request)
        assertTrue(json.contains("\"booking_id\":\"b1\""))
        assertTrue(json.contains("\"card_last_four\":\"4242\""))
        assertTrue(json.contains("\"cardholder_name\":\"Juan\""))
        assertEquals("mock", request.paymentProvider)
        assertEquals("credit_card", request.paymentMethod)
        assertEquals("USD", request.currency)
    }

    @Test
    fun paymentResponse_deserializesCorrectly() {
        val json = """{"id":"p1","booking_id":"b1","status":"confirmed","amount":"714.0"}"""
        val response = gson.fromJson(json, PaymentResponse::class.java)
        assertEquals("p1", response.id)
        assertEquals("b1", response.bookingId)
        assertEquals("confirmed", response.status)
    }

    @Test
    fun paymentConfirmRequest_serializesCorrectly() {
        val request = PaymentConfirmRequest(
            providerTransactionId = "MOCK-TXN-123",
            paymentTimestamp = "2026-05-01T12:00:00Z"
        )
        val json = gson.toJson(request)
        assertTrue(json.contains("\"provider_transaction_id\":\"MOCK-TXN-123\""))
        assertTrue(json.contains("\"payment_timestamp\":\"2026-05-01T12:00:00Z\""))
    }

    @Test
    fun bookingResponse_deserializesWithQrFields() {
        val json = """{"id":"b1","booking_code":"TH-2026-TEST","status":"confirmed",
            "qr_code":"base64data","qr_is_valid":true,"payment_id":"p1"}"""
        val response = gson.fromJson(json, BookingResponse::class.java)
        assertEquals("b1", response.id)
        assertEquals("base64data", response.qrCode)
        assertEquals(true, response.qrIsValid)
        assertEquals("p1", response.paymentId)
    }

    @Test
    fun bookingResponse_nullQrFields_whenNotPresent() {
        val json = """{"id":"b1","status":"pending"}"""
        val response = gson.fromJson(json, BookingResponse::class.java)
        assertNull(response.qrCode)
        assertNull(response.qrIsValid)
        assertNull(response.paymentId)
    }

    @Test
    fun roomImageResponse_deserializesCorrectly() {
        val json = """{"id":"img1","room_id":"r1","url":"https://s3.example.com/img.jpg","created_at":"2026-01-01"}"""
        val response = gson.fromJson(json, RoomImageResponse::class.java)
        assertEquals("img1", response.id)
        assertEquals("r1", response.roomId)
        assertEquals("https://s3.example.com/img.jpg", response.url)
    }

    @Test
    fun fcmTokenRequest_serializesCorrectly() {
        val request = FcmTokenRequest(fcmToken = "device-token-123")
        val json = gson.toJson(request)
        assertTrue(json.contains("\"fcm_token\":\"device-token-123\""))
        assertEquals("android", request.platform)
    }
}
