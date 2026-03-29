package com.example.travelhubapp_mobile.data

import com.example.travelhubapp_mobile.network.LoginRequest
import com.example.travelhubapp_mobile.network.ProfileResponse
import com.example.travelhubapp_mobile.network.RegisterRequest
import com.example.travelhubapp_mobile.network.RetrofitClient
import java.io.IOException

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val message: String) : AuthResult<Nothing>()
}

class AuthRepository(private val tokenManager: TokenManager) {

    private val api = RetrofitClient.api

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        identificationNumber: String
    ): AuthResult<String> {
        return try {
            val response = api.register(
                RegisterRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phone = phone,
                    password = password,
                    identificationNumber = identificationNumber
                )
            )
            if (response.isSuccessful) {
                AuthResult.Success(response.body()?.message ?: "Registro exitoso")
            } else {
                val error = response.errorBody()?.string() ?: "Error en registro"
                AuthResult.Error(error)
            }
        } catch (e: IOException) {
            AuthResult.Error("Error de conexión: ${e.message}")
        }
    }

    suspend fun login(email: String, password: String): AuthResult<String> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                val token = response.body()?.accessToken
                if (token != null) {
                    tokenManager.saveToken(token)
                    AuthResult.Success(token)
                } else {
                    AuthResult.Error("No se recibió token")
                }
            } else {
                val error = response.errorBody()?.string() ?: "Credenciales inválidas"
                AuthResult.Error(error)
            }
        } catch (e: IOException) {
            AuthResult.Error("Error de conexión: ${e.message}")
        }
    }

    suspend fun getProfile(): AuthResult<ProfileResponse> {
        return try {
            val token = tokenManager.getToken()
                ?: return AuthResult.Error("No hay sesión activa")
            val response = api.getProfile("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let { AuthResult.Success(it) }
                    ?: AuthResult.Error("Perfil vacío")
            } else {
                AuthResult.Error("Error al obtener perfil")
            }
        } catch (e: IOException) {
            AuthResult.Error("Error de conexión: ${e.message}")
        }
    }

    suspend fun logout() {
        tokenManager.clearToken()
    }

    suspend fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }
}
