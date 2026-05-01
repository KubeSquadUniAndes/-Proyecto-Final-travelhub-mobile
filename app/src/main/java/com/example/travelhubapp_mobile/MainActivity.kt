package com.example.travelhubapp_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import coil.Coil
import coil.ImageLoader
import com.example.travelhubapp_mobile.network.RetrofitClient
import com.example.travelhubapp_mobile.navigation.AuthNavGraph
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .okHttpClient(RetrofitClient.unsafeHttpClient)
                .crossfade(true)
                .build()
        )
        enableEdgeToEdge()
        setContent {
            TravelHubAppMobileTheme {
                AuthNavGraph()
            }
        }
    }
}
