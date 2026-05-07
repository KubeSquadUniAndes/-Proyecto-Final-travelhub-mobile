package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.travelhubapp_mobile.network.BookingResponse
import com.example.travelhubapp_mobile.ui.components.THBottomBar
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Gray100
import com.example.travelhubapp_mobile.ui.theme.Gray200
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.White
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel

@Composable
fun MisReservasScreen(
    onHome: () -> Unit,
    onPerfil: () -> Unit,
    onBuscarMas: () -> Unit,
    onBookingClick: (String) -> Unit,
    onPagar: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel: HotelViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.fetchBookings()
    }

    LaunchedEffect(viewModel.myBookings) {
        val roomIds = viewModel.myBookings.mapNotNull { it.roomId }.distinct()
        if (roomIds.isNotEmpty()) viewModel.fetchAllRoomImages(roomIds)
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
                        ReservaCard(
                            booking,
                            imageUrl = booking.roomId?.let { viewModel.roomImagesMap[it] },
                            onClick = { booking.id?.let { onBookingClick(it) } },
                            onPagar = { booking.id?.let { onPagar(it) } }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onBuscarMas,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("btn_buscar_mas"),
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
fun ReservaCard(booking: BookingResponse, imageUrl: String? = null, onClick: () -> Unit, onPagar: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth().testTag("reserva_card_${booking.id}"),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            // Image
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .background(Gray200),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl ?: "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=400")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto habitación",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
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
                    Spacer(Modifier.height(4.dp))
                    val (badgeColor, textColor) = when {
                        booking.paymentId != null && booking.status == "pending" -> Pair(
                            androidx.compose.ui.graphics.Color(0xFFE8F5E9),
                            androidx.compose.ui.graphics.Color(0xFF2E7D32)
                        )
                        booking.status == "confirmed" -> Pair(
                            androidx.compose.ui.graphics.Color(0xFFE8F5E9),
                            androidx.compose.ui.graphics.Color(0xFF2E7D32)
                        )
                        booking.status in listOf("pending", "pending_payment", "created") -> Pair(
                            androidx.compose.ui.graphics.Color(0xFFFFF8E1),
                            androidx.compose.ui.graphics.Color(0xFFF57F17)
                        )
                        booking.status in listOf("cancelled", "rejected") -> Pair(
                            androidx.compose.ui.graphics.Color(0xFFFFEBEE),
                            androidx.compose.ui.graphics.Color(0xFFC62828)
                        )
                        booking.status == "checked_in" -> Pair(Blue100, Blue600)
                        else -> Pair(Gray200, Gray600)
                    }
                    val displayStatus = when {
                        booking.paymentId != null && booking.status == "pending" -> "Confirmada"
                        else -> booking.statusDisplay ?: booking.status ?: "Sin estado"
                    }
                    Box(
                        modifier = Modifier
                            .background(badgeColor, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            displayStatus,
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor,
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
                    val isPending = booking.status in listOf("pending", "pending_payment", "created")
                            && booking.paymentId == null
                    if (isPending) {
                        Button(
                            onClick = onPagar,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("btn_pagar_${booking.id}")
                        ) {
                            Text("Pagar", style = MaterialTheme.typography.labelMedium, color = White)
                        }
                    }
                }
            }
        }
    }
}
