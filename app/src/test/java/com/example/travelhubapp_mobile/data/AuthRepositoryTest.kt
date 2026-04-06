package com.example.travelhubapp_mobile.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var tokenManager: TokenManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tokenManager = TokenManager(context)
        authRepository = AuthRepository(tokenManager)
    }

    @Test
    fun isLoggedIn_returnsFalseWhenNoToken() = runBlocking {
        tokenManager.clearToken()
        assertFalse(authRepository.isLoggedIn())
    }

    @Test
    fun isLoggedIn_returnsTrueWhenTokenExists() = runBlocking {
        tokenManager.saveToken("fake_token")
        assertTrue(authRepository.isLoggedIn())
        tokenManager.clearToken()
    }

    @Test
    fun logout_clearsToken() = runBlocking {
        tokenManager.saveToken("to_logout")
        assertTrue(authRepository.isLoggedIn())
        authRepository.logout()
        assertFalse(authRepository.isLoggedIn())
    }

    @Test
    fun getProfile_failsWithNoToken() = runBlocking {
        tokenManager.clearToken()
        val result = authRepository.getProfile()
        assertTrue(result is AuthResult.Error)
        val error = result as AuthResult.Error
        assertTrue(error.message.contains("No hay sesión"))
    }

    @Test
    fun login_failsWithInvalidServer() = runBlocking {
        val result = authRepository.login("bad@email.com", "badpass")
        assertTrue(result is AuthResult.Error)
    }

    @Test
    fun register_failsWithInvalidServer() = runBlocking {
        val result = authRepository.register(
            "Test", "User", "t@t.com", "123", "pass123!",
            "Colombia", "Bogotá", "1995-01-01", "CC", "999"
        )
        assertTrue(result is AuthResult.Error)
    }
}
