package com.example.travelhubapp_mobile.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.HotelResponse
import com.example.travelhubapp_mobile.network.HotelSearchRequest
import com.example.travelhubapp_mobile.network.RetrofitClient
import com.example.travelhubapp_mobile.network.RoomResponse
import kotlinx.coroutines.launch

class HotelViewModel(private val tokenManager: TokenManager) : ViewModel() {
    var destino by mutableStateOf("")
    var checkIn by mutableStateOf("")
    var checkOut by mutableStateOf("")
    var guests by mutableStateOf("2")

    var searchResults by mutableStateOf<List<HotelResponse>>(emptyList())
        private set

    var roomResults by mutableStateOf<List<RoomResponse>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun searchHotels(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val request = HotelSearchRequest(
                    destination = destino,
                    checkIn = checkIn,
                    checkOut = checkOut,
                    guests = guests.toIntOrNull() ?: 2
                )
                val response = RetrofitClient.api.searchHotels(request)
                if (response.isSuccessful) {
                    searchResults = response.body() ?: emptyList()
                    onSuccess()
                } else {
                    error = "Error al buscar hoteles: ${response.message()}"
                }
            } catch (e: Exception) {
                error = "Error de red: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun searchRooms(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val token = tokenManager.getToken()
                if (token == null) {
                    error = "Sesión no válida"
                    return@launch
                }

                val response = RetrofitClient.api.searchRooms(
                    token = "Bearer $token",
                    checkin = checkIn,
                    checkout = checkOut,
                    destination = destino.ifBlank { null },
                    guests = guests.toIntOrNull()
                )

                if (response.isSuccessful) {
                    roomResults = response.body() ?: emptyList()
                    onSuccess()
                } else {
                    error = "Error al buscar habitaciones: ${response.message()}"
                }
            } catch (e: Exception) {
                error = "Error de red: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
