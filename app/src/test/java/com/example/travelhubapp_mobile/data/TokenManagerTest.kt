package com.example.travelhubapp_mobile.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class TokenManagerTest {

    private lateinit var tokenManager: TokenManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tokenManager = TokenManager(context)
    }

    @Test
    fun saveAndGetToken() = runBlocking {
        tokenManager.saveToken("test_token_123")
        val token = tokenManager.getToken()
        assertEquals("test_token_123", token)
    }

    @Test
    fun clearToken_removesToken() = runBlocking {
        tokenManager.saveToken("to_be_cleared")
        tokenManager.clearToken()
        val token = tokenManager.getToken()
        assertNull(token)
    }

    @Test
    fun getToken_returnsNullWhenEmpty() = runBlocking {
        tokenManager.clearToken()
        val token = tokenManager.getToken()
        assertNull(token)
    }

    @Test
    fun saveToken_overwritesPrevious() = runBlocking {
        tokenManager.saveToken("first_token")
        tokenManager.saveToken("second_token")
        val token = tokenManager.getToken()
        assertEquals("second_token", token)
    }
}
