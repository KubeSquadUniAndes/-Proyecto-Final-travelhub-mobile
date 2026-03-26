package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

data class Hotel(val name: String, val rating: String, val price: String, val reviews: String = "")

val sampleHotels = listOf(
    Hotel("Hotel Grand Luxury", "4.8", "COP 600,000"),
    Hotel("Modern Boutique Hotel", "4.6", "COP 480,000"),
    Hotel("Beachfront Paradise Resort", "4.9", "COP 800,000"),
)

@Composable
fun BuscarHotelesScreen(onBack: () -> Unit, onHotelClick: () -> Unit) {
    Column(Modifier.fillMaxSize().background(White)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().background(White).padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onBack, modifier = Modifier.size(43.dp).clip(CircleShape)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", modifier = Modifier.size(23.dp))
                }
                Column {
                    Text("Resultados", style = MaterialTheme.typography.headlineSmall)
                    Text("Selecciona fechas", style = MaterialTheme.typography.bodyMedium, color = Gray600)
                }
            }
            IconButton(onClick = {}, modifier = Modifier.size(43.dp).clip(CircleShape)) {
                Icon(Icons.Default.Tune, "Filter", modifier = Modifier.size(23.dp))
            }
        }

        // Count
        Box(
            modifier = Modifier.fillMaxWidth().background(White).padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Gray50, RoundedCornerShape(4.dp)).padding(8.dp)
        ) {
            Text("${sampleHotels.size} hoteles encontrados", style = MaterialTheme.typography.bodyMedium, color = Gray600)
        }

        // Hotel List
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Gray50),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleHotels) { hotel -> HotelCard(hotel, onHotelClick) }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun HotelCard(hotel: Hotel, onClick: () -> Unit) {
    Card(
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column {
            // Image placeholder
            Box(
                modifier = Modifier.fillMaxWidth().height(191.dp).background(Blue100),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Hotel, null, tint = Blue600, modifier = Modifier.size(48.dp))
            }
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(hotel.name, style = MaterialTheme.typography.titleLarge)
                StarRating(hotel.rating)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${hotel.price}/ noche", style = MaterialTheme.typography.headlineLarge, color = Blue600)
                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text("Ver detalle", style = MaterialTheme.typography.labelMedium, color = White)
                    }
                }
            }
        }
    }
}
