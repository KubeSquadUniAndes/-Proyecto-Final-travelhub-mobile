package com.example.travelhubapp_mobile.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.BookingRequest
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.network.HotelResponse
import com.example.travelhubapp_mobile.network.HotelSearchRequest
import com.example.travelhubapp_mobile.network.PaymentConfirmRequest
import com.example.travelhubapp_mobile.network.PaymentRequest
import com.example.travelhubapp_mobile.network.PaymentResponse
import com.example.travelhubapp_mobile.network.RetrofitClient
import com.example.travelhubapp_mobile.network.RoomImageResponse
import com.example.travelhubapp_mobile.network.RoomResponse
import java.io.IOException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HotelViewModel(private val tokenManager: TokenManager) : ViewModel() {
    var destino by mutableStateOf("")
    var checkIn by mutableStateOf("")
    var checkOut by mutableStateOf("")
    var guests by mutableStateOf("2")

    var searchResults by mutableStateOf<List<HotelResponse>>(emptyList())
    var roomResults by mutableStateOf<List<RoomResponse>>(emptyList())
    var selectedRoom by mutableStateOf<RoomResponse?>(null)
    var lastBooking by mutableStateOf<BookingResponse?>(null)
    var myBookings by mutableStateOf<List<BookingResponse>>(emptyList())
    var selectedBookingDetails by mutableStateOf<BookingResponse?>(null)
    var roomImages by mutableStateOf<List<RoomImageResponse>>(emptyList())
    var roomImagesMap by mutableStateOf<Map<String, String>>(emptyMap())
    var lastPayment by mutableStateOf<PaymentResponse?>(null)
    var selectedBookingForPayment by mutableStateOf<BookingResponse?>(null)

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    var skipNetworkForTests = false

    fun searchHotels(onSuccess: () -> Unit) {
        if (skipNetworkForTests) return
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
            } catch (e: IOException) {
                error = "Error de red: ${e.localizedMessage}"
            } catch (e: HttpException) {
                error = "Error HTTP: ${e.message()}"
            } finally {
                isLoading = false
            }
        }
    }

    fun searchRooms(onSuccess: () -> Unit) {
        if (skipNetworkForTests) return
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
                    fetchAllRoomImages(roomResults.map { it.id })
                    onSuccess()
                } else {
                    error = "Error al buscar habitaciones: ${response.message()}"
                }
            } catch (e: IOException) {
                error = "Error de red: ${e.message}"
            } catch (e: HttpException) {
                error = "Error HTTP: ${e.message}"
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
        if (skipNetworkForTests) return
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
                    lastBooking = response.body()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    error = when {
                        errorBody.contains("schedule conflict") -> "La habitación no está disponible para las fechas seleccionadas"
                        errorBody.contains("anomaly") -> "No se pudo completar la reserva. Intenta de nuevo"
                        else -> "Error al crear reserva: ${response.message()}"
                    }
                }
            } catch (e: IOException) {
                error = "Error de red: ${e.message}"
            } catch (e: HttpException) {
                error = "Error HTTP: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun createPayment(
        bookingId: String,
        amount: Double,
        cardLastFour: String,
        cardholderName: String,
        cardholderEmail: String,
        onSuccess: () -> Unit
    ) {
        if (skipNetworkForTests) return
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val token = tokenManager.getToken() ?: run {
                    error = "Sesión no válida"
                    return@launch
                }
                val response = RetrofitClient.api.createPayment(
                    token = "Bearer $token",
                    request = PaymentRequest(
                        bookingId = bookingId,
                        amount = amount,
                        cardLastFour = cardLastFour,
                        cardholderName = cardholderName,
                        cardholderEmail = cardholderEmail
                    )
                )
                if (response.isSuccessful) {
                    lastPayment = response.body()
                    val timestamp = java.text.SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.ROOT
                    ).format(java.util.Date())
                    val confirmResponse = RetrofitClient.api.confirmPayment(
                        paymentId = bookingId,
                        token = "Bearer $token",
                        request = PaymentConfirmRequest(
                            providerTransactionId = "MOCK-TXN-${System.currentTimeMillis()}",
                            paymentTimestamp = timestamp
                        )
                    )
                    if (confirmResponse.isSuccessful) {
                        lastPayment = confirmResponse.body()
                    }
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    error = when {
                        errorBody.contains("already exists") -> "Esta reserva ya tiene un pago registrado"
                        else -> "Error al procesar pago: ${response.message()}"
                    }
                }
            } catch (e: IOException) {
                error = "Error de red: ${e.message}"
            } catch (e: HttpException) {
                error = "Error HTTP: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchAllRoomImages(roomIds: List<String>) {
        if (skipNetworkForTests) return
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: return@launch
            val map = mutableMapOf<String, String>()
            roomIds.map { id ->
                launch {
                    try {
                        val response = RetrofitClient.api.getRoomImages(id, "Bearer $token")
                        if (response.isSuccessful) {
                            response.body()?.firstOrNull()?.url?.let { map[id] = it }
                        }
                    } catch (_: Exception) {}
                }
            }.forEach { it.join() }
            roomImagesMap = map.toMap()
        }
    }

    fun fetchRoomImages(roomId: String) {
        if (skipNetworkForTests) return
        roomImages = emptyList()
        viewModelScope.launch {
            try {
                val token = tokenManager.getToken() ?: return@launch
                val response = RetrofitClient.api.getRoomImages(roomId, "Bearer $token")
                if (response.isSuccessful) roomImages = response.body() ?: emptyList()
            } catch (e: IOException) {
                roomImages = emptyList()
                error = e.message
            } catch (e: HttpException) {
                roomImages = emptyList()
                error = e.message
            }
        }
    }

    fun fetchBookings() {
        if (skipNetworkForTests) return
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
            } catch (e: IOException) {
                error = "Error de red: ${e.message}"
            } catch (e: HttpException) {
                error = "Error HTTP: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchBookingDetails(bookingId: String) {
        if (skipNetworkForTests) return
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
            } catch (e: IOException) {
                error = "Error de red: ${e.message}"
            } catch (e: HttpException) {
                error = "Error HTTP: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
