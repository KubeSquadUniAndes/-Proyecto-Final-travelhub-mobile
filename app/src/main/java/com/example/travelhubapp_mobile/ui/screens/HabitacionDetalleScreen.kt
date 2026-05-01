package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.travelhubapp_mobile.ui.components.CardShape
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Gray200
import com.example.travelhubapp_mobile.ui.theme.Gray50
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.Gray700
import com.example.travelhubapp_mobile.ui.theme.White
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel

@Composable
fun HabitacionDetalleScreen(
    onBack: () -> Unit,
    onReservar: () -> Unit,
    viewModel: HotelViewModel
) {
    val room = viewModel.selectedRoom ?: return

    LaunchedEffect(room.id) {
        viewModel.fetchRoomImages(room.id)
    }

    Box(Modifier.fillMaxSize().background(White)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Galería de imágenes
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Blue100)
                        .testTag("galeria_imagenes"),
                    contentAlignment = Alignment.Center
                ) {
                    if (viewModel.roomImages.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            items(viewModel.roomImages) { img ->
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(img.url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Foto habitación",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .fillMaxHeight()
                                )
                            }
                        }
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Foto habitación",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            // Info principal
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Nombre y tipo
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            room.hotelName ?: "Hotel",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.testTag("detalle_hotel_nombre")
                        )
                        Text(
                            room.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Gray600,
                            modifier = Modifier.testTag("detalle_habitacion_nombre")
                        )
                        room.roomType?.let {
                            Box(
                                modifier = Modifier
                                    .background(Blue100, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    it.replaceFirstChar { c -> c.uppercase() },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Blue600,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = Gray200, thickness = 0.5.dp)

                    // Capacidad, camas y tamaño
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier.testTag("detalle_capacidad_row")
                    ) {
                        InfoChip(
                            icon = {
                                Icon(
                                    Icons.Default.People,
                                    null,
                                    tint = Blue600,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            label = "${room.capacity} personas"
                        )
                        room.beds?.let {
                            InfoChip(
                                icon = {
                                    Icon(
                                        Icons.Default.Bed,
                                        null,
                                        tint = Blue600,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                label = it
                            )
                        }
                        room.size?.let {
                            InfoChip(
                                icon = {
                                    Icon(
                                        Icons.Default.SquareFoot,
                                        null,
                                        tint = Blue600,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                label = "${it.toInt()} m²"
                            )
                        }
                    }

                    HorizontalDivider(color = Gray200, thickness = 0.5.dp)

                    // Amenities
                    room.amenities?.takeIf { it.isNotBlank() }?.let { amenitiesStr ->
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Servicios incluidos", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            amenitiesStr.split(",").forEach { amenity ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.testTag("amenity_${amenity.trim()}")
                                ) {
                                    Icon(Icons.Default.CheckCircle, null, tint = Blue600, modifier = Modifier.size(16.dp))
                                    Text(amenity.trim(), style = MaterialTheme.typography.bodyMedium, color = Gray700)
                                }
                            }
                        }
                        HorizontalDivider(color = Gray200, thickness = 0.5.dp)
                    }

                    // Reseñas placeholder
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Reseñas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.Star, null, tint = Blue600, modifier = Modifier.size(18.dp))
                            Text("4.5", style = MaterialTheme.typography.titleMedium, color = Blue600, fontWeight = FontWeight.Bold)
                        }
                        Card(
                            shape = CardShape,
                            colors = CardDefaults.cardColors(containerColor = Gray50),
                            elevation = CardDefaults.cardElevation(0.dp),
                            modifier = Modifier.testTag("resena_card")
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    repeat(5) {
                                        Icon(Icons.Default.Star, null, tint = Blue600, modifier = Modifier.size(14.dp))
                                    }
                                }
                                Text(
                                    "Excelente habitación, muy limpia y cómoda.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Gray700
                                )
                                Text("— Usuario verificado", style = MaterialTheme.typography.labelSmall, color = Gray600)
                            }
                        }
                    }

                    // Precio
                    HorizontalDivider(color = Gray200, thickness = 0.5.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Precio por noche", style = MaterialTheme.typography.labelMedium, color = Gray600)
                            Text(
                                "$${room.price}",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Blue600,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.testTag("detalle_precio")
                            )
                        }
                        room.status?.let {
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (it == "disponible") Blue100 else Gray200,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    it.replaceFirstChar { c -> c.uppercase() },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (it == "disponible") Blue600 else Gray600,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Header flotante
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(White.copy(alpha = 0.9f))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", modifier = Modifier.size(20.dp))
            }
        }

        // Botón reservar fijo abajo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(White)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Button(
                onClick = onReservar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_reservar"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue600)
            ) {
                Text("Reservar", style = MaterialTheme.typography.titleMedium, color = White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun InfoChip(icon: @Composable () -> Unit, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        icon()
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Gray700, fontSize = 13.sp)
    }
}
