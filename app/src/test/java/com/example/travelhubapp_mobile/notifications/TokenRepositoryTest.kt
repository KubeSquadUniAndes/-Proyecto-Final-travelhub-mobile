package com.example.travelhubapp_mobile.notifications

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class TokenRepositoryTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        TokenRepository.saveToken(context, "")
    }

    @Test
    fun saveAndGetToken_returnsCorrectToken() {
        TokenRepository.saveToken(context, "test-fcm-token-123")
        assertEquals("test-fcm-token-123", TokenRepository.getToken(context))
    }

    @Test
    fun getToken_returnsNullWhenNotSet() {
        val freshContext = ApplicationProvider.getApplicationContext<Context>()
        val prefs = freshContext.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
        prefs.edit().remove("fcm_token").apply()
        assertNull(TokenRepository.getToken(freshContext))
    }

    @Test
    fun saveToken_overwritesPreviousToken() {
        TokenRepository.saveToken(context, "first-token")
        TokenRepository.saveToken(context, "second-token")
        assertEquals("second-token", TokenRepository.getToken(context))
    }

    @Test
    fun saveToken_handlesLongToken() {
        val longToken = "a".repeat(500)
        TokenRepository.saveToken(context, longToken)
        assertEquals(longToken, TokenRepository.getToken(context))
    }
}
