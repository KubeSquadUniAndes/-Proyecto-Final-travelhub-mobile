package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.BluGradient
import com.example.travelhubapp_mobile.ui.components.THLogo
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun BienvenidaScreen(onLogin: () -> Unit, onRegistro: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(BluGradient).padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.weight(1f))

        // Logo + Branding
        THLogo()
        Spacer(Modifier.height(24.dp))
        Text("TravelHub", style = MaterialTheme.typography.displayLarge, color = White)
        Spacer(Modifier.height(8.dp))
        Text(
            "Descubre los mejores hospedajes\npara tu próximo viaje",
            style = MaterialTheme.typography.bodyLarge,
            color = Blue100,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        // CA1: Opciones "Iniciar sesión" y "Registrarme"
        // CA5: Solo opciones para viajeros en móvil (sin registro hotel)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            // Botón Iniciar sesión
            Button(
                onClick = onLogin,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = White),
                modifier = Modifier.fillMaxWidth().height(59.dp)
            ) {
                Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge, color = Blue600)
            }

            // Botón Registrarme como viajero
            OutlinedButton(
                onClick = onRegistro,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = White),
                border = ButtonDefaults.outlinedButtonBorder(true).copy(brush = androidx.compose.ui.graphics.SolidColor(White)),
                modifier = Modifier.fillMaxWidth().height(59.dp)
            ) {
                Text("Registrarme como viajero", style = MaterialTheme.typography.titleLarge, color = White)
            }
        }

        Spacer(Modifier.height(24.dp))
        Text(
            "Al continuar, aceptas nuestros Términos de servicio y Política de privacidad",
            style = MaterialTheme.typography.bodySmall,
            color = White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}
