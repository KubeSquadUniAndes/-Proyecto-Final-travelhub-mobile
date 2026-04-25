package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.THButton
import com.example.travelhubapp_mobile.ui.components.THDatePicker
import com.example.travelhubapp_mobile.ui.components.THInput
import com.example.travelhubapp_mobile.ui.theme.*
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaScreen(onBack: () -> Unit, viewModel: HotelViewModel) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // Payment fields
    var cardNumber by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.ROOT) }
    
    val nights = remember(viewModel.checkIn, viewModel.checkOut) {
        try {
            val start = dateFormat.parse(viewModel.checkIn.substringBefore('T'))
            val end = dateFormat.parse(viewModel.checkOut.substringBefore('T'))
            if (start != null && end != null) {
                val diff = end.time - start.time
                TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt().coerceAtLeast(1)
            } else 1
        } catch (_: Exception) { 1 }
    }

    val pricePerNight = 600000.0 // Hardcoded for example as per image (1,800,000 / 3)
    val totalAmount = nights * pricePerNight
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply { 
        maximumFractionDigits = 0 
    } }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Finalizar reserva", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = White
                    )
                )
                HorizontalDivider(color = Gray200, thickness = 0.5.dp)
            }
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                color = White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total ($nights noches):", style = MaterialTheme.typography.bodyLarge, color = Gray600)
                        Text(
                            currencyFormatter.format(totalAmount).replace("$", "COP "),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Blue600,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    THButton(
                        text = "Confirmar reserva",
                        onClick = { /* TODO: Implement confirmation */ },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        containerColor = Gray50
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ResumenCard(
                checkIn = viewModel.checkIn.substringBefore('T'),
                checkOut = viewModel.checkOut.substringBefore('T'),
                guests = viewModel.guests,
                nights = nights.toString()
            )
            
            DatosPersonalesCard(
                nombre = nombre,
                onNombreChange = { nombre = it },
                email = email,
                onEmailChange = { email = it },
                telefono = telefono,
                onTelefonoChange = { telefono = it }
            )
            
            DetallesReservaCard(viewModel)

            MetodoPagoCard(
                cardNumber, { cardNumber = it },
                cardName, { cardName = it },
                expiry, { expiry = it },
                cvv, { cvv = it }
            )
            
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ResumenCard(checkIn: String, checkOut: String, guests: String, nights: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Resumen de la reserva",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Blue100),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Hotel, null, tint = Blue600, modifier = Modifier.size(40.dp))
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Hotel Grand Luxury", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, null, tint = StarYellow, modifier = Modifier.size(16.dp))
                        Text(" 4.8", style = MaterialTheme.typography.bodyMedium, color = Gray600)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            ResumenItem("Check-in:", checkIn)
            ResumenItem("Check-out:", checkOut)
            ResumenItem("Huéspedes:", guests)
            ResumenItem("Noches:", nights)
        }
    }
}

@Composable
private fun ResumenItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Gray600, style = MaterialTheme.typography.bodyLarge)
        Text(value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun DatosPersonalesCard(
    nombre: String, onNombreChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    telefono: String, onTelefonoChange: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                "Datos personales",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            THInput(value = nombre, onValueChange = onNombreChange, label = "Nombre completo", placeholder = "Juan Pérez")
            THInput(value = email, onValueChange = onEmailChange, label = "Email", placeholder = "correo@ejemplo.com")
            THInput(value = telefono, onValueChange = onTelefonoChange, label = "Teléfono", placeholder = "+57 300 123 4567")
        }
    }
}

@Composable
private fun DetallesReservaCard(viewModel: HotelViewModel) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                "Detalles de la reserva",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.ROOT) }
            val checkInTime = try { dateFormatter.parse(viewModel.checkIn.substringBefore('T'))?.time } catch (_: Exception) { null }
            val checkOutTime = try { dateFormatter.parse(viewModel.checkOut.substringBefore('T'))?.time } catch (_: Exception) { null }

            THDatePicker(
                value = viewModel.checkIn.substringBefore('T'),
                onValueChange = { viewModel.checkIn = "${it}T12:00:00" },
                label = "Check-in",
                placeholder = "Selecciona fecha",
                minDate = System.currentTimeMillis(),
                maxDate = checkOutTime
            )

            THDatePicker(
                value = viewModel.checkOut.substringBefore('T'),
                onValueChange = { viewModel.checkOut = "${it}T12:00:00" },
                label = "Check-out",
                placeholder = "Selecciona fecha",
                minDate = checkInTime ?: System.currentTimeMillis()
            )

            THInput(
                value = viewModel.guests,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || (newValue.all { it.isDigit() } && (newValue.toIntOrNull() ?: 0) <= 20)) {
                        viewModel.guests = newValue
                    }
                },
                label = "Número de huéspedes",
                placeholder = "2",
                leadingIcon = Icons.Default.Group
            )
        }
    }
}

@Composable
private fun MetodoPagoCard(
    number: String, onNumberChange: (String) -> Unit,
    name: String, onNameChange: (String) -> Unit,
    expiry: String, onExpiryChange: (String) -> Unit,
    cvv: String, onCvvChange: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.CreditCard, null, tint = Gray900)
                Text(
                    "Método de pago",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            THInput(value = number, onValueChange = onNumberChange, label = "Número de tarjeta", placeholder = "1234 5678 9012 3456")
            THInput(value = name, onValueChange = onNameChange, label = "Nombre en la tarjeta", placeholder = "JUAN PEREZ")
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    THInput(value = expiry, onValueChange = onExpiryChange, label = "Vencimiento", placeholder = "MM/AA")
                }
                Box(modifier = Modifier.weight(1f)) {
                    THInput(value = cvv, onValueChange = onCvvChange, label = "CVV", placeholder = "123")
                }
            }
        }
    }
}
