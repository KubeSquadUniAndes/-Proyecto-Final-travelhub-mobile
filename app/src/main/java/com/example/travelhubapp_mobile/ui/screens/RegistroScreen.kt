package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun RegistroScreen(onRegister: () -> Unit, onLogin: () -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(BluGradient).verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(16.dp))
        THBackButton(onBack)

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            THLogo()
            Text("Crear cuenta", style = MaterialTheme.typography.displayLarge, color = White)
            Text("Únete a TravelHub y descubre tu próximo destino", style = MaterialTheme.typography.bodyLarge, color = Blue100)
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).clip(FormShape).background(White).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            THInput(name, { name = it }, "Nombre completo", "Juan Pérez García", Icons.Default.Person)
            THInput(email, { email = it }, "Correo electrónico", "correo@ejemplo.com", Icons.Default.Email)
            THInput(phone, { phone = it }, "Teléfono móvil", "3001234567", Icons.Default.Phone)
            THInput(password, { password = it }, "Contraseña", "Mínimo 8 caracteres", Icons.Default.Lock, isPassword = true)
            THInput(confirmPassword, { confirmPassword = it }, "Confirmar contraseña", "Repite tu contraseña", Icons.Default.Lock, isPassword = true)
            Spacer(Modifier.height(4.dp))
            THButton("Crear cuenta", onClick = onRegister)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿Ya tienes cuenta? ", style = MaterialTheme.typography.bodyLarge, color = Gray600)
                TextButton(onClick = onLogin) {
                    Text("Inicia sesión", style = MaterialTheme.typography.titleMedium, color = Blue600)
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}
