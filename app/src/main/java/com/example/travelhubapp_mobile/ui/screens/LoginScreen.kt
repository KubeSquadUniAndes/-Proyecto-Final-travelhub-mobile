package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*

@Composable
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(BluGradient).verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))
        THBackButton(onBack)

        // Header
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            THLogo()
            Text("TravelHub", style = MaterialTheme.typography.displayLarge, color = White)
            Text("Bienvenido de nuevo", style = MaterialTheme.typography.bodyLarge, color = Blue100)
        }

        // Form Card
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).clip(FormShape).background(White).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            THInput(email, { email = it }, "Correo electrónico", "correo@ejemplo.com", Icons.Default.Email)
            THInput(password, { password = it }, "Contraseña", "••••••••", Icons.Default.Lock, isPassword = true)
            TextButton(onClick = {}) {
                Text("¿Olvidaste tu contraseña?", color = Blue600, style = MaterialTheme.typography.titleSmall)
            }
            THButton("Iniciar sesión", onClick = onLogin)
        }

        // Divider
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(Modifier.weight(1f), color = Gray300)
            Text("  o regístrate aquí  ", style = MaterialTheme.typography.bodyMedium, color = Gray500)
            HorizontalDivider(Modifier.weight(1f), color = Gray300)
        }

        // Register link
        TextButton(onClick = onRegister, modifier = Modifier.fillMaxWidth()) {
            Text("Crear cuenta nueva", color = White, style = MaterialTheme.typography.titleMedium)
        }

        // Terms
        Text(
            "Al continuar, aceptas nuestros Términos de servicio y Política de privacidad",
            style = MaterialTheme.typography.bodySmall,
            color = White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 16.dp)
        )
    }
}
