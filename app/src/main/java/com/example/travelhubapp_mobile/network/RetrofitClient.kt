package com.example.travelhubapp_mobile.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate

object RetrofitClient {

    private const val BASE_URL = "https://k8s-workload-travelhu-6ebf4c6317-8eaeeae129c46548.elb.us-east-1.amazonaws.com/"

    private val hostInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .header("Host", "api.travelhub.com")
            .build()
        chain.proceed(request)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // TrustManager que acepta todos los certificados (SOLO para desarrollo)
    private object UnsafeTrustManager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    private fun unsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<X509TrustManager>(UnsafeTrustManager)
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustAllCerts, java.security.SecureRandom())
        }

        return OkHttpClient.Builder()
            .addInterceptor(hostInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0])
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    private val client = unsafeOkHttpClient()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
