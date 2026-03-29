package com.example.travelhubapp_mobile.network

import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class ModelsTest {

    private val gson = Gson()

    @Test
    fun registerRequest_serializesCorrectly() {
        val request = RegisterRequest(
            firstName = "Juan",
            lastName = "Pérez",
            email = "juan@test.com",
            phone = "+57 300 123 4567",
            password = "SecurePass123!",
            identificationNumber = "1234567890"
        )
        val json = gson.toJson(request)
        assertTrue(json.contains("\"first_name\":\"Juan\""))
        assertTrue(json.contains("\"last_name\":\"Pérez\""))
        assertTrue(json.contains("\"email\":\"juan@test.com\""))
        assertTrue(json.contains("\"user_type\":\"traveler\""))
        assertTrue(json.contains("\"identification_number\":\"1234567890\""))
        assertTrue(json.contains("\"identification_type\":\"CC\""))
    }

    @Test
    fun registerRequest_hasDefaultValues() {
        val request = RegisterRequest(
            firstName = "Test", lastName = "User",
            email = "t@t.com", phone = "123", password = "pass"
        )
        assertEquals("Colombia", request.country)
        assertEquals("Bogotá", request.city)
        assertEquals("traveler", request.userType)
        assertEquals("CC", request.identificationType)
        assertEquals("1995-01-01", request.birthDate)
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
        val json = """{"id":"123","email":"test@mail.com","full_name":"Juan Pérez","status":"active"}"""
        val response = gson.fromJson(json, ProfileResponse::class.java)
        assertEquals("123", response.id)
        assertEquals("test@mail.com", response.email)
        assertEquals("Juan Pérez", response.fullName)
        assertEquals("active", response.status)
        assertNull(response.firstName)
        assertNull(response.lastName)
    }

    @Test
    fun profileResponse_deserializesWithFirstLastName() {
        val json = """{"id":"456","first_name":"Ana","last_name":"García","email":"ana@mail.com"}"""
        val response = gson.fromJson(json, ProfileResponse::class.java)
        assertEquals("Ana", response.firstName)
        assertEquals("García", response.lastName)
        assertNull(response.fullName)
    }

    @Test
    fun registerResponse_deserializesCorrectly() {
        val json = """{"id":"uuid-123","message":"Created","email":"test@mail.com"}"""
        val response = gson.fromJson(json, RegisterResponse::class.java)
        assertEquals("uuid-123", response.id)
        assertEquals("Created", response.message)
        assertEquals("test@mail.com", response.email)
    }
}
