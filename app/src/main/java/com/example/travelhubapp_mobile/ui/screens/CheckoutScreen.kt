package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun CheckoutScreen(onBack: () -> Unit, onConfirm: () -> Unit) {
    var nombre by remember { mutableStateOf("Juan Pérez") }
    var email by remember { mutableStateOf("correo@ejemplo.com") }
    var telefono by remember { mutableStateOf("+57 300 123 4567") }

    Column(Modifier.fillMaxSize().background(White)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
            Text("Checkout", style = MaterialTheme.typography.headlineSmall)
        }

        Column(
            modifier = Modifier.fillMaxSize().background(Gray50).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resumen
            SectionCard("Resumen de la reserva") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.size(95.dp).clip(CardShape).background(Blue100), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Hotel, null, tint = Blue600, modifier = Modifier.size(32.dp))
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Hotel Grand Luxury", style = MaterialTheme.typography.titleLarge)
                        Text("★ 4.8", style = MaterialTheme.typography.bodyMedium, color = Gray600)
                    }
                }
                HorizontalDivider(color = Gray200)
                InfoRow("Check-in:", "2026-03-15")
                InfoRow("Check-out:", "2026-03-18")
                InfoRow("Huéspedes:", "2")
                InfoRow("Noches:", "3")
            }

            // Datos personales
            SectionCard("Datos personales") {
                THInput(nombre, { nombre = it }, "Nombre completo", "")
                THInput(email, { email = it }, "Email", "")
                THInput(telefono, { telefono = it }, "Teléfono", "")
            }

            // Total
            SectionCard("Resumen de pago") {
                InfoRow("3 noches x COP 600,000", "COP 1,800,000")
                InfoRow("Impuestos (19%)", "COP 342,000")
                HorizontalDivider(color = Gray200)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total", style = MaterialTheme.typography.headlineMedium)
                    Text("COP 2,142,000", style = MaterialTheme.typography.headlineMedium, color = Blue600)
                }
            }

            THButton("Confirmar reserva", onClick = onConfirm)
            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(CardShape).background(White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        content()
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Gray600)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}
