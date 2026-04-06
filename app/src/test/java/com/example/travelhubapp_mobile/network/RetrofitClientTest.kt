package com.example.travelhubapp_mobile.network

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class RetrofitClientTest {

    @Test
    fun api_isNotNull() {
        assertNotNull(RetrofitClient.api)
    }

    @Test
    fun api_isSameInstance() {
        val api1 = RetrofitClient.api
        val api2 = RetrofitClient.api
        assertSame(api1, api2)
    }

    @Test
    fun api_implementsApiService() {
        assertTrue(RetrofitClient.api is ApiService)
    }
}
