package com.example.travelhubapp_mobile.data

import org.junit.Assert.*
import org.junit.Test

class AuthResultTest {

    @Test
    fun success_holdsData() {
        val result = AuthResult.Success("token123")
        assertEquals("token123", result.data)
    }

    @Test
    fun error_holdsMessage() {
        val result = AuthResult.Error("Something failed")
        assertEquals("Something failed", result.message)
    }

    @Test
    fun success_isCorrectType() {
        val result: AuthResult<String> = AuthResult.Success("ok")
        assertTrue(result is AuthResult.Success)
        assertFalse(result is AuthResult.Error)
    }

    @Test
    fun error_isCorrectType() {
        val result: AuthResult<String> = AuthResult.Error("fail")
        assertTrue(result is AuthResult.Error)
        assertFalse(result is AuthResult.Success)
    }

    @Test
    fun success_withDifferentTypes() {
        val intResult = AuthResult.Success(42)
        assertEquals(42, intResult.data)

        val listResult = AuthResult.Success(listOf("a", "b"))
        assertEquals(2, listResult.data.size)
    }

    @Test
    fun whenExpression_worksCorrectly() {
        val success: AuthResult<String> = AuthResult.Success("data")
        val error: AuthResult<String> = AuthResult.Error("error")

        val successValue = when (success) {
            is AuthResult.Success -> success.data
            is AuthResult.Error -> null
        }
        assertEquals("data", successValue)

        val errorValue = when (error) {
            is AuthResult.Success -> null
            is AuthResult.Error -> error.message
        }
        assertEquals("error", errorValue)
    }
}
