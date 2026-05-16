package com.example.travelhubapp_mobile.ui.screens

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Schedule
import com.example.travelhubapp_mobile.data.QrCacheManager
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Gray100
import com.example.travelhubapp_mobile.ui.theme.Gray200
import com.example.travelhubapp_mobile.ui.theme.Gray400
import com.example.travelhubapp_mobile.ui.theme.Gray50
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.Gray700
import com.example.travelhubapp_mobile.ui.theme.StarYellow
import com.example.travelhubapp_mobile.ui.theme.White
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaPrintScreen(
    bookingId: String,
    onBack: () -> Unit,
    onHome: () -> Unit,
    viewModel: HotelViewModel,
) {
    val context = LocalContext.current

    LaunchedEffect(bookingId) {
        viewModel.fetchBookingDetails(bookingId)
    }

    val booking = viewModel.selectedBookingDetails

    LaunchedEffect(booking?.qrCode) {
        booking?.id?.let { id ->
            booking.qrCode?.let { qr -> QrCacheManager.saveQr(context, id, qr) }
        }
    }

    LaunchedEffect(booking?.roomId) {
        booking?.roomId?.let { viewModel.fetchRoomImages(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles de reserva", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        containerColor = Gray50
    ) { innerPadding ->
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Blue600)
            }
        } else if (booking != null) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Status Banner
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                booking.statusDisplay ?: "Reserva Confirmada",
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Número de reserva: #${booking.bookingCode}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }

                // Hotel Card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column {
                        // Image
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Gray200),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        viewModel.roomImages.firstOrNull()?.url
                                            ?: "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800"
                                    )
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Foto habitación",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                booking.roomType?.replaceFirstChar { it.uppercase() } ?: "Hotel Grand Luxury",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, null, tint = StarYellow, modifier = Modifier.size(16.dp))
                                Text(" 4.8 (342 reseñas)", style = MaterialTheme.typography.bodyMedium, color = Gray600)
                            }
                            Text(
                                "Hotel de lujo ubicado en el corazón de la ciudad con vistas panorámicas y servicios de primera clase.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Gray600
                            )
                        }
                    }
                }

                // Reservation Details
                DetailSection("Detalles de la reserva") {
                    IconDetailItem(Icons.Default.CalendarMonth, "Check-in", "A partir de las 15:00")
                    IconDetailItem(Icons.Default.CalendarMonth, "Check-out", "Hasta las 12:00")
                    IconDetailItem(Icons.Default.People, "Número de huéspedes", "${booking.numGuests} personas")
                    val nightsLabel = if (booking.totalNights == 1) "noche" else "noches"
                    IconDetailItem(
                        Icons.Default.Schedule,
                        "Duración",
                        "${booking.totalNights} $nightsLabel"
                    )
                }

                // Guest Info
                DetailSection("Información del huésped") {
                    LabeledInfo("Nombre:", booking.travelerName ?: "")
                    LabeledInfo("Email:", booking.travelerEmail ?: "")
                    LabeledInfo("Teléfono:", booking.travelerPhone ?: "")
                }

                // Payment Summary
                DetailSection("Resumen de pago") {
                    PriceRow("Precio por noche:", "COP ${booking.pricePerNight}")
                    PriceRow("Número de noches:", "x ${booking.totalNights}")
                    PriceRow("Subtotal:", "COP ${booking.totalPrice}")
                    PriceRow("Impuestos (19%):", "COP ${booking.taxes}")
                    HorizontalDivider(color = Gray100, modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total pagado:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "COP ${booking.finalPrice}",
                            color = Blue600,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }

                // Hotel Contact
                // QR Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val cachedQr = remember(bookingId) {
                            QrCacheManager.getQr(context, bookingId)
                        }
                        val qrBase64 = booking.qrCode ?: cachedQr
                        val isExpired = remember(booking.startTime) {
                            try {
                                val fmt = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ROOT)
                                val checkIn = fmt.parse(booking.startTime?.substringBefore('T') ?: "")
                                checkIn != null && java.util.Date().after(checkIn)
                            } catch (_: Exception) { false }
                        }
                        val canShowQr = !qrBase64.isNullOrBlank() &&
                            booking.qrIsValid != false &&
                            !isExpired

                        if (canShowQr) {
                            val bitmap = remember(qrBase64) {
                                try {
                                    val bytes = Base64.decode(qrBase64, Base64.DEFAULT)
                                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                                } catch (_: Exception) { null }
                            }
                            if (bitmap != null) {
                                androidx.compose.foundation.Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "QR Check-in",
                                    modifier = Modifier
                                        .size(200.dp)
                                        .border(1.dp, Gray200, RoundedCornerShape(8.dp))
                                )
                                if (cachedQr != null && booking.qrCode == null) {
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        "📵 Modo sin conexión",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Gray600
                                    )
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(180.dp)
                                    .border(1.dp, Gray200, RoundedCornerShape(8.dp))
                                    .background(Gray50, RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.QrCode2,
                                        null,
                                        tint = Gray400,
                                        modifier = Modifier.size(64.dp)
                                    )
                                    Text(
                                        when {
                                            isExpired -> "QR expirado"
                                            booking.qrIsValid == false -> "QR invalidado"
                                            else -> "QR disponible al\nconfirmar reserva"
                                        },
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Gray600,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        Text("Código QR Check-in", fontWeight = FontWeight.Bold)
                        Text(
                            when {
                                canShowQr -> "Escanea al llegar al hotel"
                                isExpired -> "La fecha de check-in ya pasó"
                                booking.qrIsValid == false -> "Reserva cancelada o invalidada"
                                else -> "El hotel debe aprobar tu reserva"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Gray600
                        )
                    }
                }

                Button(
                    onClick = onHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gray200, contentColor = Gray700)
                ) {
                    Text("Volver al inicio", fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun DetailSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

@Composable
fun IconDetailItem(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Blue600, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Gray600)
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LabeledInfo(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Text(label, modifier = Modifier.width(80.dp), color = Gray600, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun PriceRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Gray600, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun IconInfo(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Blue600, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodySmall, color = Gray700)
    }
}
