package com.example.travelhubapp_mobile.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AuthRepositoryExtendedTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var tokenManager: TokenManager
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        tokenManager = TokenManager(context)
        authRepository = AuthRepository(tokenManager, context)
    }

    @Test
    fun isLoggedIn_returnsFalseAfterClearToken() = runBlocking {
        tokenManager.clearToken()
        assertFalse(authRepository.isLoggedIn())
    }

    @Test
    fun isLoggedIn_returnsTrueAfterSaveToken() = runBlocking {
        tokenManager.saveToken("some-token")
        assertTrue(authRepository.isLoggedIn())
        tokenManager.clearToken()
    }

    @Test
    fun logout_makesIsLoggedInFalse() = runBlocking {
        tokenManager.saveToken("some-token")
        assertTrue(authRepository.isLoggedIn())
        authRepository.logout()
        assertFalse(authRepository.isLoggedIn())
    }

    @Test
    fun getProfile_noToken_returnsError() = runBlocking {
        tokenManager.clearToken()
        val result = authRepository.getProfile()
        assertTrue(result is AuthResult.Error)
        assertEquals("No hay sesión activa", (result as AuthResult.Error).message)
    }

    @Test
    fun getProfile_withToken_failsNetworkButReturnsError() = runBlocking {
        tokenManager.saveToken("valid-token-but-no-server")
        val result = authRepository.getProfile()
        // No server, so it must be an error
        assertTrue(result is AuthResult.Error)
    }

    @Test
    fun login_networkError_returnsConnectionError() = runBlocking {
        val result = authRepository.login("test@example.com", "password123")
        assertTrue(result is AuthResult.Error)
        val error = result as AuthResult.Error
        assertNotNull(error.message)
    }

    @Test
    fun login_emptyCredentials_returnsError() = runBlocking {
        val result = authRepository.login("", "")
        assertTrue(result is AuthResult.Error)
    }

    @Test
    fun register_emptyFields_returnsError() = runBlocking {
        val result = authRepository.register(
            "", "", "", "", "", "", "", "", "", ""
        )
        assertTrue(result is AuthResult.Error)
    }

    @Test
    fun authRepository_createdWithoutContext_worksForBasicOps() = runBlocking {
        val repoNoContext = AuthRepository(tokenManager)
        tokenManager.clearToken()
        assertFalse(repoNoContext.isLoggedIn())
    }

    @Test
    fun authRepository_logoutIdempotent() = runBlocking {
        tokenManager.clearToken()
        authRepository.logout()
        authRepository.logout()
        assertFalse(authRepository.isLoggedIn())
    }

    @Test
    fun authResult_success_isAccessible() {
        val success: AuthResult<String> = AuthResult.Success("token-data")
        assertTrue(success is AuthResult.Success)
        assertEquals("token-data", (success as AuthResult.Success).data)
    }

    @Test
    fun authResult_error_isAccessible() {
        val error: AuthResult<String> = AuthResult.Error("Connection refused")
        assertTrue(error is AuthResult.Error)
        assertEquals("Connection refused", (error as AuthResult.Error).message)
    }

    @Test
    fun login_malformedEmail_returnsError() = runBlocking {
        val result = authRepository.login("not-an-email", "pass")
        assertTrue(result is AuthResult.Error)
    }

    @Test
    fun getProfile_afterLogout_returnsError() = runBlocking {
        tokenManager.saveToken("some-token")
        authRepository.logout()
        val result = authRepository.getProfile()
        assertTrue(result is AuthResult.Error)
        assertEquals("No hay sesión activa", (result as AuthResult.Error).message)
    }
}
