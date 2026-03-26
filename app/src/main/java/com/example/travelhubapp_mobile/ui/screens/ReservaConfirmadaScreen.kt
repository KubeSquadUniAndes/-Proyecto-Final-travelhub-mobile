package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun ReservaConfirmadaScreen(onHome: () -> Unit, onPrint: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(White).verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        // Success icon
        Box(Modifier.size(95.dp).clip(CircleShape).background(Success), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.CheckCircle, null, tint = White, modifier = Modifier.size(63.dp))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("¡Reserva confirmada!", style = MaterialTheme.typography.displayLarge, color = Gray900)
            Text("Tu reserva ha sido procesada exitosamente", style = MaterialTheme.typography.bodyLarge, color = Gray600)
        }

        // Reservation number
        Column(
            modifier = Modifier.fillMaxWidth().clip(FormShape).background(White).border(1.dp, Gray200, FormShape).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Número de reserva", style = MaterialTheme.typography.bodyMedium, color = Gray600)
            Text("#123456", style = MaterialTheme.typography.displayLarge, color = Blue600)
        }

        // Action buttons
        THOutlineButton("Descargar confirmación", onClick = onPrint, icon = Icons.Default.Download)
        THOutlineButton("Compartir reserva", onClick = {}, icon = Icons.Default.Share)

        // Info banner
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp))
                .background(Blue100).border(1.dp, Blue600, RoundedCornerShape(4.dp)).padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Confirmación enviada: Hemos enviado los detalles de tu reserva a tu correo electrónico",
                style = MaterialTheme.typography.titleSmall, color = Blue900
            )
        }

        THButton("Volver al inicio", onClick = onHome)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("¿Necesitas ayuda?", style = MaterialTheme.typography.bodyMedium, color = Gray600)
            Text("+57 300 123 4567", style = MaterialTheme.typography.titleSmall, color = Blue600)
        }
    }
}
