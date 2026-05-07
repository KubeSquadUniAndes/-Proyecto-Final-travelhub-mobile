package com.example.travelhubapp_mobile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import coil.Coil
import coil.ImageLoader
import com.example.travelhubapp_mobile.network.RetrofitClient
import com.example.travelhubapp_mobile.navigation.AuthNavGraph
import com.example.travelhubapp_mobile.notifications.TokenRepository
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .okHttpClient(RetrofitClient.unsafeHttpClient)
                .crossfade(true)
                .build()
        )
        requestNotificationPermission()
        fetchFcmToken()
        enableEdgeToEdge()
        setContent {
            TravelHubAppMobileTheme {
                AuthNavGraph()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0
                )
            }
        }
    }

    private fun fetchFcmToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            android.util.Log.d("FCM_TOKEN", "Token: $token")
            TokenRepository.saveToken(applicationContext, token)
        }
    }
}
