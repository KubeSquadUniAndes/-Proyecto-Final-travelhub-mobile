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
    val status: String?
)
