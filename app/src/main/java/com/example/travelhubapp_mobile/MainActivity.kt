package com.example.travelhubapp_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.travelhubapp_mobile.navigation.TravelHubNavGraph
import com.example.travelhubapp_mobile.ui.theme.TravelHubAppMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelHubAppMobileTheme {
                TravelHubNavGraph()
            }
        }
    }
}
