package com.example.travelhubapp_mobile.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("users/api/v1/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("login-handler/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("login-handler/api/v1/auth/me")
    suspend fun getProfile(@Header("Authorization") token: String): Response<ProfileResponse>

    @GET("users/health")
    suspend fun healthUsers(): Response<Any>

    @GET("login-handler/health")
    suspend fun healthAuth(): Response<Any>

    @POST("hotels/api/v1/hotels/search")
    suspend fun searchHotels(@Body request: HotelSearchRequest): Response<List<HotelResponse>>

    @GET("hospedajes/api/v1/rooms/search")
    suspend fun searchRooms(
        @Header("Authorization") token: String,
        @Query("checkin") checkin: String,
        @Query("checkout") checkout: String,
        @Query("destination") destination: String? = null,
        @Query("guests") guests: Int? = null
    ): Response<List<RoomResponse>>

    @POST("reservas/api/v1/bookings/")
    suspend fun createBooking(
        @Header("Authorization") token: String,
        @Body request: BookingRequest
    ): Response<BookingResponse>

    @GET("reservas/api/v1/bookings/")
    suspend fun getBookings(
        @Header("Authorization") token: String
    ): Response<List<BookingResponse>>

    @GET("reservas/api/v1/bookings/{id}")
    suspend fun getBookingDetails(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<BookingResponse>

    @GET("hospedajes/api/v1/rooms/{roomId}/images")
    suspend fun getRoomImages(
        @Path("roomId") roomId: String,
        @Header("Authorization") token: String
    ): Response<List<RoomImageResponse>>

    @PATCH("reservas/api/v1/bookings/{id}/approve")
    suspend fun approveBooking(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<BookingResponse>

    @POST("login-handler/api/v1/auth/fcm-token")
    suspend fun registerFcmToken(
        @Header("Authorization") token: String,
        @Body request: FcmTokenRequest
    ): Response<Unit>

    @POST("pagos/api/v1/payments")
    suspend fun createPayment(
        @Header("Authorization") token: String,
        @Body request: PaymentRequest
    ): Response<PaymentResponse>

    @POST("pagos/api/v1/payments/{bookingId}/confirm")
    suspend fun confirmPayment(
        @Path("bookingId") paymentId: String,
        @Header("Authorization") token: String,
        @Body request: PaymentConfirmRequest
    ): Response<PaymentResponse>
}
