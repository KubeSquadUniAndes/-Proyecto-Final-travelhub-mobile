package com.example.travelhubapp_mobile.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelhubapp_mobile.network.HotelResponse
import com.example.travelhubapp_mobile.network.HotelSearchRequest
import com.example.travelhubapp_mobile.network.RetrofitClient
import kotlinx.coroutines.launch

class HotelViewModel : ViewModel() {
    var destino by mutableStateOf("")
    var checkIn by mutableStateOf("")
    var checkOut by mutableStateOf("")
    var guests by mutableStateOf("2 adultos")

    var searchResults by mutableStateOf<List<HotelResponse>>(emptyList())
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
                    guests = 2 // Simplificado para este ejemplo
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
}
