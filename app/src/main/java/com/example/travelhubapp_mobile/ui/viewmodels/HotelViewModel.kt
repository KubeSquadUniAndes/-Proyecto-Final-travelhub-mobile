package com.example.travelhubapp_mobile.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.*
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

    var selectedRoom by mutableStateOf<RoomResponse?>(null)
    var lastBooking by mutableStateOf<BookingResponse?>(null)
    var myBookings by mutableStateOf<List<BookingResponse>>(emptyList())
        private set

    var selectedBookingDetails by mutableStateOf<BookingResponse?>(null)

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

    fun createBooking(
        travelerName: String,
        travelerEmail: String,
        travelerPhone: String,
        travelerDocument: String,
        onSuccess: () -> Unit
    ) {
        val room = selectedRoom ?: return
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val token = tokenManager.getToken()
                if (token == null) {
                    error = "Sesión no válida"
                    return@launch
                }

                val request = BookingRequest(
                    hotelId = room.hotelId,
                    roomId = room.id,
                    startTime = checkIn.ifBlank { "2026-05-01T15:00:00Z" },
                    endTime = checkOut.ifBlank { "2026-05-05T11:00:00Z" },
                    roomType = room.roomType ?: "Deluxe",
                    numGuests = guests.toIntOrNull() ?: 2,
                    pricePerNight = room.price.toDoubleOrNull() ?: 150.0,
                    travelerName = travelerName,
                    travelerEmail = travelerEmail,
                    travelerPhone = travelerPhone,
                    travelerDocument = travelerDocument
                )

                val response = RetrofitClient.api.createBooking(
                    token = "Bearer $token",
                    request = request
                )

                if (response.isSuccessful) {
                    val createdBooking = response.body()
                    lastBooking = createdBooking
                    
                    // Approve the booking
                    createdBooking?.id?.let { bookingId ->
                        val approveResponse = RetrofitClient.api.approveBooking(
                            id = bookingId,
                            token = "Bearer $token"
                        )
                        if (approveResponse.isSuccessful) {
                            lastBooking = approveResponse.body()
                        }
                    }

                    onSuccess()
                } else {
                    error = "Error al crear reserva: ${response.message()}"
                }
            } catch (e: Exception) {
                error = "Error de red: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchBookings() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val token = tokenManager.getToken()
                if (token == null) {
                    error = "Sesión no válida"
                    return@launch
                }

                val response = RetrofitClient.api.getBookings("Bearer $token")
                if (response.isSuccessful) {
                    myBookings = response.body() ?: emptyList()
                } else {
                    error = "Error al obtener reservas: ${response.message()}"
                }
            } catch (e: Exception) {
                error = "Error de red: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchBookingDetails(bookingId: String) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val token = tokenManager.getToken()
                if (token == null) {
                    error = "Sesión no válida"
                    return@launch
                }

                val response = RetrofitClient.api.getBookingDetails(bookingId, "Bearer $token")
                if (response.isSuccessful) {
                    selectedBookingDetails = response.body()
                } else {
                    error = "Error al obtener detalles: ${response.message()}"
                }
            } catch (e: Exception) {
                error = "Error de red: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
