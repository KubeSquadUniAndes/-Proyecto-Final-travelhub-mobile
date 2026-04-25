package com.example.travelhubapp_mobile.network

import com.example.travelhubapp_mobile.network.RoomResponse
import retrofit2.Response
import retrofit2.http.*

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
}
