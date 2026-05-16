package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.travelhubapp_mobile.network.RoomResponse
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
fun BuscarHotelesScreen(onBack: () -> Unit, onHotelClick: () -> Unit, viewModel: HotelViewModel) {
    Column(Modifier.fillMaxSize().background(White)) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onBack, modifier = Modifier.size(43.dp).clip(CircleShape)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", modifier = Modifier.size(23.dp))
                }
                Column {
                    val location = viewModel.roomResults.firstOrNull()?.destination ?: viewModel.destino
                    Text("Resultados", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        "$location • ${viewModel.checkIn.substringBefore('T')}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray600
                    )
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
            Text("${viewModel.roomResults.size} habitaciones encontradas", style = MaterialTheme.typography.bodyMedium, color = Gray600)
        }

        // Room List
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Gray50),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.roomResults) { room ->
                RoomCard(room, imageUrl = viewModel.roomImagesMap[room.id], onClick = {
                    viewModel.selectedRoom = room
                    onHotelClick()
                })
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomCard(room: RoomResponse, imageUrl: String? = null, onClick: () -> Unit) {
    Card(
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.testTag("room_card_${room.id}")
    ) {
        Column {
            // Room Image
            Box(
                modifier = Modifier.fillMaxWidth().height(180.dp).background(Blue100),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Foto habitación",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
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
                Box(
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
                        .background(White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = room.roomType?.replaceFirstChar { it.uppercase() } ?: "Habitación",
                        style = MaterialTheme.typography.labelMedium,
                        color = Blue600,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Hotel Name and Room Name
                Column {
                    Text(room.hotelName ?: "Hotel", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(room.name, style = MaterialTheme.typography.bodyLarge, color = Gray600)
                    room.destination?.let {
                        Text(it, style = MaterialTheme.typography.labelMedium, color = Blue600, fontWeight = FontWeight.Bold)
                    }
                }

                // Capacity and Beds
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.People, null, tint = Gray600, modifier = Modifier.size(16.dp))
                        Text("${room.capacity} pers.", style = MaterialTheme.typography.bodyMedium, color = Gray600)
                    }
                    room.beds?.let {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Bed, null, tint = Gray600, modifier = Modifier.size(16.dp))
                            Text(it, style = MaterialTheme.typography.bodyMedium, color = Gray600)
                        }
                    }
                }

                // Amenities
                room.amenities?.split(",")?.let { amenities ->
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        amenities.take(3).forEach { amenity ->
                            SuggestionChip(
                                onClick = { },
                                label = { Text(amenity.trim(), fontSize = 12.sp) },
                                shape = RoundedCornerShape(8.dp),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = Gray50,
                                    labelColor = Gray700
                                ),
                                border = SuggestionChipDefaults.suggestionChipBorder(borderColor = Gray200, enabled = true)
                            )
                        }
                    }
                }

                HorizontalDivider(color = Gray200, thickness = 0.5.dp)

                // Price and Action
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Desde", style = MaterialTheme.typography.labelSmall, color = Gray600)
                        Text(
                            "$${room.price}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Blue600,
                            fontWeight = FontWeight.Bold
                        )
                        Text("por noche", style = MaterialTheme.typography.labelSmall, color = Gray600)
                    }
                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text("Ver detalle", style = MaterialTheme.typography.titleSmall, color = White)
                    }
                }
            }
        }
    }
}
