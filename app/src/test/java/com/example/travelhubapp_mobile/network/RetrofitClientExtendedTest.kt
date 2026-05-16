package com.example.travelhubapp_mobile.network

import okhttp3.OkHttpClient
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class RetrofitClientExtendedTest {

    @Test
    fun api_isNotNull_repeated() {
        assertNotNull(RetrofitClient.api)
    }

    @Test
    fun api_isSingleton() {
        val api1 = RetrofitClient.api
        val api2 = RetrofitClient.api
        assertSame(api1, api2)
    }

    @Test
    fun unsafeHttpClient_isNotNull() {
        assertNotNull(RetrofitClient.unsafeHttpClient)
    }

    @Test
    fun unsafeHttpClient_isOkHttpClient() {
        assertTrue(RetrofitClient.unsafeHttpClient is OkHttpClient)
    }

    @Test
    fun api_implementsApiService() {
        assertTrue(RetrofitClient.api is ApiService)
    }
}
