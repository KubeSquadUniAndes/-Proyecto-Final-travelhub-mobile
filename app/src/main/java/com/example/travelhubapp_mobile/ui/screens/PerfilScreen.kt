package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.BluGradient
import com.example.travelhubapp_mobile.ui.components.CardShape
import com.example.travelhubapp_mobile.ui.components.THBottomBar
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Blue50
import com.example.travelhubapp_mobile.ui.theme.Destructive
import com.example.travelhubapp_mobile.ui.theme.Gray100
import com.example.travelhubapp_mobile.ui.theme.Gray400
import com.example.travelhubapp_mobile.ui.theme.Gray50
import com.example.travelhubapp_mobile.ui.theme.Gray500
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.Gray700
import com.example.travelhubapp_mobile.ui.theme.SuccessDark
import com.example.travelhubapp_mobile.ui.theme.SuccessLight
import com.example.travelhubapp_mobile.ui.theme.White

@Composable
fun PerfilScreen(onLogout: () -> Unit, onHome: () -> Unit, onMisReservas: () -> Unit) {
    Scaffold(
        bottomBar = {
            THBottomBar(
                selected = 2,
                onSelect = { 
                    when(it) {
                        0 -> onHome()
                        1 -> onMisReservas()
                    }
                },
                onLogout = onLogout
            )
        }
    ) { innerPadding ->
    Column(
        Modifier.fillMaxSize().background(White)
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        PerfilHeader()
        Column(
            Modifier.background(Gray50).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Reservas recientes", style = MaterialTheme.typography.headlineSmall)
            ReservationItem(
                name = "Hotel Grand Luxury",
                date = "15-18 Mar 2026",
                status = "Confirmada",
                id = "#453821",
                statusBg = SuccessLight,
                statusColor = SuccessDark
            )
            ReservationItem(
                name = "Beachfront Paradise Resort",
                date = "22-25 Ene 2026",
                status = "Completada",
                id = "#398562",
                statusBg = Gray100,
                statusColor = Gray700
            )
            Text("Mi cuenta", style = MaterialTheme.typography.headlineSmall)
            Column(Modifier.fillMaxWidth().clip(CardShape).background(White)) {
                AccountMenuItem("Información personal", "Editar nombre, teléfono, etc.", Icons.Default.Person, Blue50)
                AccountMenuItem("Métodos de pago", "Tarjetas y cuentas", Icons.Default.CreditCard, SuccessLight)
                AccountMenuItem("Notificaciones", "Preferencias de alertas", Icons.Default.Notifications, Color(0xFFFFF7ED))
                AccountMenuItem("Seguridad", "Contraseña y acceso", Icons.Default.Lock, Color(0xFFFCE7F3))
            }
            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Destructive),
                modifier = Modifier.fillMaxWidth().height(50.dp).testTag("btn_logout")
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, null, modifier = Modifier.size(19.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(Modifier.height(80.dp))
        }
    }
    }
}

@Composable
private fun PerfilHeader() {
    Column(
        modifier = Modifier.fillMaxWidth().background(BluGradient).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                Modifier.size(79.dp).clip(CircleShape).background(White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = White, modifier = Modifier.size(39.dp))
            }
            Column {
                Text("Mi Perfil", style = MaterialTheme.typography.headlineLarge, color = White)
                Text("Viajero TravelHub", style = MaterialTheme.typography.bodyMedium, color = Blue100)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("8", "Reservas", Modifier.weight(1f))
            StatCard("1250", "Puntos", Modifier.weight(1f))
            StatCard("★ 4.9", "Rating", Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clip(CardShape).background(White.copy(alpha = 0.15f)).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, style = MaterialTheme.typography.headlineLarge, color = White)
        Text(label, style = MaterialTheme.typography.bodySmall, color = Blue100)
    }
}

@Composable
private fun ReservationItem(
    name: String, date: String, status: String,
    id: String, statusBg: Color, statusColor: Color
) {
    Column(
        Modifier.fillMaxWidth().clip(CardShape).background(White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(name, style = MaterialTheme.typography.titleMedium)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.CalendarMonth, null, tint = Gray500, modifier = Modifier.size(13.dp))
                    Text(date, style = MaterialTheme.typography.bodyMedium, color = Gray600)
                }
            }
            Text(
                status,
                style = MaterialTheme.typography.bodySmall,
                color = statusColor,
                modifier = Modifier.clip(CircleShape).background(statusBg)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
        Text("Reserva $id", style = MaterialTheme.typography.bodySmall, color = Gray500)
    }
}

@Composable
private fun AccountMenuItem(title: String, subtitle: String, icon: ImageVector, iconBg: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            Modifier.size(39.dp).clip(CircleShape).background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Gray700, modifier = Modifier.size(19.dp))
        }
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = Gray500)
        }
        Icon(Icons.Default.ChevronRight, null, tint = Gray400, modifier = Modifier.size(19.dp))
    }
}
