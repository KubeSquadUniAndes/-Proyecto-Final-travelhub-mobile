package com.example.travelhubapp_mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.data.Hospedaje
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun HospedajeCard(hospedaje: Hospedaje, onReservar: (Hospedaje) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Image placeholder
            Box(
                modifier = Modifier.fillMaxWidth().height(180.dp).background(Blue100),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Hotel, null, tint = Blue600, modifier = Modifier.size(48.dp))
            }

            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Nombre
                Text(hospedaje.nombre, style = MaterialTheme.typography.titleLarge, color = Gray900)

                // Ubicación
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.LocationOn, null, tint = Gray500, modifier = Modifier.size(16.dp))
                    Text(hospedaje.ubicacion, style = MaterialTheme.typography.bodyMedium, color = Gray600)
                }

                // Rating
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.Star, null, tint = StarYellow, modifier = Modifier.size(15.dp))
                    Text(hospedaje.rating, style = MaterialTheme.typography.titleSmall, color = Gray900)
                    Text(hospedaje.reviews, style = MaterialTheme.typography.bodySmall, color = Gray500)
                }

                // Precio + Botón Reservar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(hospedaje.precioPorNoche, style = MaterialTheme.typography.headlineLarge, color = Blue600)
                        Text("por noche", style = MaterialTheme.typography.bodySmall, color = Gray500)
                    }
                    Button(
                        onClick = { onReservar(hospedaje) },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text("Reservar", style = MaterialTheme.typography.labelMedium, color = White)
                    }
                }
            }
        }
    }
}
