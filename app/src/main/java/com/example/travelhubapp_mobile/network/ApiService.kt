package com.example.travelhubapp_mobile.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("users/api/v1/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("auth/api/v1/auth/me")
    suspend fun getProfile(@Header("Authorization") token: String): Response<ProfileResponse>

    @GET("users/health")
    suspend fun healthUsers(): Response<Any>

    @GET("auth/health")
    suspend fun healthAuth(): Response<Any>
}
