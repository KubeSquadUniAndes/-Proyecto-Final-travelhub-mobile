package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun PerfilScreen(onLogout: () -> Unit) {
    Column(Modifier.fillMaxSize().background(White).verticalScroll(rememberScrollState())) {
        // Header with gradient
        Column(
            modifier = Modifier.fillMaxWidth().background(BluGradient).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(Modifier.size(79.dp).clip(CircleShape).background(White.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Person, null, tint = White, modifier = Modifier.size(39.dp))
                }
                Column {
                    Text("María García", style = MaterialTheme.typography.headlineLarge, color = White)
                    Text("maria.garcia@email.com", style = MaterialTheme.typography.bodyMedium, color = Blue100)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("8", "Reservas", Modifier.weight(1f))
                StatCard("1250", "Puntos", Modifier.weight(1f))
                StatCard("★ 4.9", "Rating", Modifier.weight(1f))
            }
        }

        Column(Modifier.background(Gray50).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Reservas recientes
            Text("Reservas recientes", style = MaterialTheme.typography.headlineSmall)
            ReservationItem("Hotel Grand Luxury", "15-18 Mar 2026", "Confirmada", "#453821", SuccessLight, SuccessDark)
            ReservationItem("Beachfront Paradise Resort", "22-25 Ene 2026", "Completada", "#398562", Gray100, Gray700)

            // Mi cuenta
            Text("Mi cuenta", style = MaterialTheme.typography.headlineSmall)
            Column(Modifier.fillMaxWidth().clip(CardShape).background(White)) {
                AccountMenuItem("Información personal", "Editar nombre, teléfono, etc.", Icons.Default.Person, Blue50)
                AccountMenuItem("Métodos de pago", "Tarjetas y cuentas", Icons.Default.CreditCard, SuccessLight)
                AccountMenuItem("Notificaciones", "Preferencias de alertas", Icons.Default.Notifications, Color(0xFFFFF7ED))
                AccountMenuItem("Seguridad", "Contraseña y acceso", Icons.Default.Lock, Color(0xFFFCE7F3))
            }

            // Logout
            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Destructive),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, null, modifier = Modifier.size(19.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clip(CardShape).background(White.copy(alpha = 0.15f)).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, style = MaterialTheme.typography.headlineLarge, color = White)
        Text(label, style = MaterialTheme.typography.bodySmall, color = Blue100)
    }
}

@Composable
fun ReservationItem(name: String, date: String, status: String, id: String, statusBg: androidx.compose.ui.graphics.Color, statusColor: androidx.compose.ui.graphics.Color) {
    Column(Modifier.fillMaxWidth().clip(CardShape).background(White).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(name, style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.CalendarMonth, null, tint = Gray500, modifier = Modifier.size(13.dp))
                    Text(date, style = MaterialTheme.typography.bodyMedium, color = Gray600)
                }
            }
            Text(status, style = MaterialTheme.typography.bodySmall, color = statusColor,
                modifier = Modifier.clip(CircleShape).background(statusBg).padding(horizontal = 12.dp, vertical = 4.dp))
        }
        Text("Reserva $id", style = MaterialTheme.typography.bodySmall, color = Gray500)
    }
}

@Composable
fun AccountMenuItem(title: String, subtitle: String, icon: ImageVector, iconBg: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(Modifier.size(39.dp).clip(CircleShape).background(iconBg), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Gray700, modifier = Modifier.size(19.dp))
        }
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = Gray500)
        }
        Icon(Icons.Default.ChevronRight, null, tint = Gray400, modifier = Modifier.size(19.dp))
    }
}
