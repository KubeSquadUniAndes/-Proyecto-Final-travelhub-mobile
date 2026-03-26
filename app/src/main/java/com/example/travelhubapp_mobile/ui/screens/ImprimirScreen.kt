package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun ImprimirScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().background(White)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
            Text("Confirmación", style = MaterialTheme.typography.headlineSmall)
        }

        Column(
            modifier = Modifier.fillMaxSize().background(Gray50).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Action buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {}, shape = CardShape, colors = ButtonDefaults.buttonColors(containerColor = Blue600), modifier = Modifier.weight(1f).height(47.dp)) {
                    Icon(Icons.Default.Print, null, modifier = Modifier.size(19.dp)); Spacer(Modifier.width(8.dp))
                    Text("Imprimir", style = MaterialTheme.typography.labelLarge)
                }
                Button(onClick = {}, shape = CardShape, colors = ButtonDefaults.buttonColors(containerColor = Success), modifier = Modifier.weight(1f).height(47.dp)) {
                    Icon(Icons.Default.Download, null, modifier = Modifier.size(19.dp)); Spacer(Modifier.width(8.dp))
                    Text("Descargar", style = MaterialTheme.typography.labelLarge)
                }
            }

            // Share
            Text("Compartir vía:", style = MaterialTheme.typography.titleSmall, color = Gray600)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ShareButton("Compartir", Icons.Default.Share, Blue100, Modifier.weight(1f))
                ShareButton("Email", Icons.Default.Email, Color(0xFFFFE2E2), Modifier.weight(1f))
                ShareButton("WhatsApp", Icons.AutoMirrored.Filled.Message, SuccessLight, Modifier.weight(1f))
                ShareButton("SMS", Icons.Default.Sms, Color(0xFFF3E8FF), Modifier.weight(1f))
            }

            // Confirmed banner
            Row(
                modifier = Modifier.fillMaxWidth().clip(CardShape).background(SuccessLight).border(1.dp, Color(0xFFB9F8CF), CardShape).padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(47.dp).clip(CircleShape).background(Success), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.CheckCircle, null, tint = White, modifier = Modifier.size(27.dp))
                }
                Column {
                    Text("Reserva Confirmada", style = MaterialTheme.typography.headlineSmall, color = SuccessDark)
                    Text("Número de reserva: #123456", style = MaterialTheme.typography.bodyMedium, color = Success)
                }
            }

            // Hotel info
            SectionCard("Hotel Grand Luxury") {
                StarRating("4.8")
                Text("Hotel de lujo ubicado en el corazón de la ciudad con vistas panorámicas y servicios de primera clase.",
                    style = MaterialTheme.typography.bodyMedium, color = Gray700)
            }

            // Reservation details
            SectionCard("Detalles de la reserva") {
                DetailRow(Icons.Default.CalendarMonth, "Check-in", "2026-03-15", "A partir de las 15:00")
                DetailRow(Icons.Default.CalendarMonth, "Check-out", "2026-03-18", "Hasta las 12:00")
                DetailRow(Icons.Default.People, "Huéspedes", "2 adultos", null)
                DetailRow(Icons.Default.NightsStay, "Noches", "3", null)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun ShareButton(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, bg: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clip(CardShape).background(White).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(Modifier.size(39.dp).clip(CircleShape).background(bg), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Gray700, modifier = Modifier.size(19.dp))
        }
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun DetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String, subtitle: String?) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Gray500, modifier = Modifier.size(19.dp))
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = Gray600)
            Text(value, style = MaterialTheme.typography.titleSmall)
            if (subtitle != null) Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Gray500)
        }
    }
}
