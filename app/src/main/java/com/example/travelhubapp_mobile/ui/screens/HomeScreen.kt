package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun HomeScreen(onSearch: () -> Unit) {
    var destino by remember { mutableStateOf("") }
    var checkIn by remember { mutableStateOf("") }
    var checkOut by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(White).verticalScroll(rememberScrollState())) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("TravelHub", style = MaterialTheme.typography.headlineLarge, color = Blue600)
        }

        // Hero
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Encuentra tu hotel ideal", style = MaterialTheme.typography.displayLarge, color = Gray900)
            Text("Miles de opciones de alojamiento al mejor precio", style = MaterialTheme.typography.bodyLarge, color = Gray600)
        }

        // Search Form Card
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).clip(FormShape).background(White)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            THInput(destino, { destino = it }, "Destino", "¿A dónde viajas?", Icons.Default.LocationOn)
            THInput(checkIn, { checkIn = it }, "Check-in", "Selecciona fecha", Icons.Default.CalendarMonth)
            THInput(checkOut, { checkOut = it }, "Check-out", "Selecciona fecha", Icons.Default.CalendarMonth)
            THInput("", {}, "Huéspedes", "2 adultos", Icons.Default.People)
            THButton("Buscar hoteles", onClick = onSearch)
        }
        Spacer(Modifier.height(80.dp))
    }
}
