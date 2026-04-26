package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.ui.components.THBottomBar
import com.example.travelhubapp_mobile.ui.theme.*
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel

@Composable
fun MisReservasScreen(
    onHome: () -> Unit,
    onPerfil: () -> Unit,
    onBuscarMas: () -> Unit,
    onBookingClick: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel: HotelViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.fetchBookings()
    }

    Scaffold(
        bottomBar = {
            THBottomBar(
                selected = 1,
                onSelect = { 
                    when(it) {
                        0 -> onHome()
                        2 -> onPerfil()
                    }
                },
                onLogout = onLogout
            )
        },
        containerColor = White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Blue600,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Mis Reservas",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "${viewModel.myBookings.size} reservas encontradas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray600
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Gray100, thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))

            if (viewModel.isLoading && viewModel.myBookings.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Blue600)
                }
            } else if (viewModel.myBookings.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes reservas aún", color = Gray600)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(viewModel.myBookings) { booking ->
                        ReservaCard(booking, onClick = { booking.id?.let { onBookingClick(it) } })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = onBuscarMas,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Blue600),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Blue600)
            ) {
                Text(
                    "Buscar más hoteles",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ReservaCard(booking: BookingResponse, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            // Placeholder for Image
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .background(Gray200),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Hotel, null, tint = Gray400, modifier = Modifier.size(40.dp))
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        booking.bookingCode ?: "Reserva",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        booking.roomType ?: "Habitación",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray600
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            null,
                            tint = StarYellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            " 4.8", // Hardcoded rating as it's not in booking response
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            "COP ${booking.pricePerNight ?: "0"}",
                            style = MaterialTheme.typography.titleLarge,
                            color = Blue600,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "por noche",
                            style = MaterialTheme.typography.labelSmall,
                            color = Gray600
                        )
                    }
                    IconButton(onClick = { /* Delete logic */ }) {
                        Icon(
                            Icons.Default.Delete,
                            null,
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
