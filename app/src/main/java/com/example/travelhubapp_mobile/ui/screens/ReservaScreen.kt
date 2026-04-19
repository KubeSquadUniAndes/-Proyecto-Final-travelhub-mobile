package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
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
import com.example.travelhubapp_mobile.ui.components.THInput
import com.example.travelhubapp_mobile.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaScreen(onBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

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
            ResumenCard()
            DatosPersonalesCard(
                nombre = nombre,
                onNombreChange = { nombre = it },
                email = email,
                onEmailChange = { email = it },
                telefono = telefono,
                onTelefonoChange = { telefono = it }
            )
            DetallesReservaCard()
            
            THButton(
                text = "Confirmar reserva",
                onClick = { /* TODO: Implement confirmation */ },
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
private fun ResumenCard() {
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
            ResumenItem("Check-in:", "2026-03-15")
            ResumenItem("Check-out:", "2026-03-18")
            ResumenItem("Huéspedes:", "2")
            ResumenItem("Noches:", "3")
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
private fun DetallesReservaCard() {
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
            DetalleIconoItem(Icons.Default.CalendarMonth, "Check-in", "15 de marzo, 2026")
            DetalleIconoItem(Icons.Default.CalendarMonth, "Check-out", "18 de marzo, 2026")
            DetalleIconoItem(Icons.Default.Group, "Huéspedes", "2 adultos")
        }
    }
}

@Composable
private fun DetalleIconoItem(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Gray600, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(value, style = MaterialTheme.typography.bodyMedium, color = Gray600)
        }
    }
}
