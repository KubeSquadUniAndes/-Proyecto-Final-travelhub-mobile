package com.example.travelhubapp_mobile.network

import com.google.gson.annotations.SerializedName

// === Register ===
data class RegisterRequest(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    val phone: String,
    val country: String,
    val city: String,
    @SerializedName("birth_date") val birthDate: String,
    val password: String,
    @SerializedName("user_type") val userType: String = "traveler",
    @SerializedName("identification_type") val identificationType: String,
    @SerializedName("identification_number") val identificationNumber: String
)

data class RegisterResponse(
    val id: String?,
    val message: String?,
    val email: String?
)

// === Login ===
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("token_type") val tokenType: String?,
    val message: String?,
    val detail: String?
)

// === Profile ===
data class ProfileResponse(
    val id: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("full_name") val fullName: String?,
    val email: String?,
    val phone: String?,
    @SerializedName("user_type") val userType: String?,
    val country: String?,
    val city: String?,
    val status: String?,
    val role: String?
)

// === Hotels ===
data class HotelSearchRequest(
    val destination: String,
    val checkIn: String,
    val checkOut: String,
    val guests: Int = 2
)

data class HotelResponse(
    val id: String,
    val name: String,
    val rating: String,
    val price: String,
    val reviews: String?,
    val location: String?,
    val image: String?
)

// === Rooms ===
data class RoomResponse(
    val id: String,
    @SerializedName("hotel_id") val hotelId: String,
    @SerializedName("hotel_name") val hotelName: String?,
    val destination: String?,
    val name: String,
    @SerializedName("room_type") val roomType: String?,
    val price: String,
    val capacity: Int,
    val beds: String?,
    val size: Double?,
    val status: String?,
    val amenities: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

// === Bookings ===
data class BookingRequest(
    @SerializedName("hotel_id") val hotelId: String,
    @SerializedName("room_id") val roomId: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("room_type") val roomType: String,
    @SerializedName("num_guests") val numGuests: Int,
    @SerializedName("price_per_night") val pricePerNight: Double,
    @SerializedName("traveler_name") val travelerName: String,
    @SerializedName("traveler_email") val travelerEmail: String,
    @SerializedName("traveler_phone") val travelerPhone: String,
    @SerializedName("traveler_document") val travelerDocument: String,
    val notes: String = "Test booking",
    @SerializedName("special_requests") val specialRequests: String = "Late check-in"
)

data class BookingResponse(
    val id: String?,
    @SerializedName("user_id") val userId: String?,
    @SerializedName("hotel_id") val hotelId: String?,
    @SerializedName("room_id") val roomId: String?,
    @SerializedName("start_time") val startTime: String?,
    @SerializedName("end_time") val endTime: String?,
    val status: String?,
    @SerializedName("status_display") val statusDisplay: String?,
    val notes: String?,
    @SerializedName("booking_code") val bookingCode: String?,
    @SerializedName("room_type") val roomType: String?,
    @SerializedName("num_guests") val numGuests: Int?,
    @SerializedName("additional_guests") val additionalGuests: List<String>?,
    @SerializedName("special_requests") val specialRequests: String?,
    @SerializedName("price_per_night") val pricePerNight: String?,
    @SerializedName("total_nights") val totalNights: Int?,
    @SerializedName("total_price") val totalPrice: String?,
    val taxes: String?,
    @SerializedName("final_price") val finalPrice: String?,
    @SerializedName("traveler_name") val travelerName: String?,
    @SerializedName("traveler_email") val travelerEmail: String?,
    @SerializedName("traveler_phone") val travelerPhone: String?,
    @SerializedName("traveler_document") val travelerDocument: String?,
    val cancellable: Boolean?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val message: String?
)
