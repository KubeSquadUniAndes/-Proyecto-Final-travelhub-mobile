package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

data class Reserva(val name: String, val rating: String, val reviews: String, val price: String)

val sampleReservas = listOf(
    Reserva("Hotel Grand Luxury", "4.8", "(342)", "COP 600,000"),
    Reserva("Beachfront Paradise Resort", "4.9", "(523)", "COP 800,000"),
    Reserva("Pool View Resort", "4.7", "(298)", "COP 720,000"),
)

@Composable
fun MisReservasScreen(onSearch: () -> Unit) {
    Column(Modifier.fillMaxSize().background(White)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Favorite, null, tint = Blue600, modifier = Modifier.size(27.dp))
            Column {
                Text("Mis Reservas", style = MaterialTheme.typography.headlineLarge)
                Text("${sampleReservas.size} hoteles guardados", style = MaterialTheme.typography.bodyMedium, color = Gray600)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Gray50),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleReservas) { reserva ->
                Card(shape = CardShape, colors = CardDefaults.cardColors(containerColor = White)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(Modifier.size(127.dp).clip(CardShape).background(Blue100), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Hotel, null, tint = Blue600, modifier = Modifier.size(32.dp))
                        }
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(reserva.name, style = MaterialTheme.typography.titleMedium)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("★", color = StarYellow)
                                Text(reserva.rating, style = MaterialTheme.typography.titleSmall)
                                Text(reserva.reviews, style = MaterialTheme.typography.bodySmall, color = Gray500)
                            }
                            Text(reserva.price, style = MaterialTheme.typography.headlineSmall, color = Blue600)
                            Text("por noche", style = MaterialTheme.typography.bodySmall, color = Gray600)
                        }
                        IconButton(onClick = {}, modifier = Modifier.size(43.dp).clip(CircleShape)) {
                            Icon(Icons.Default.Delete, "Delete", tint = Destructive, modifier = Modifier.size(19.dp))
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.height(8.dp))
                THOutlineButton("Buscar más hoteles", onClick = onSearch)
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}
