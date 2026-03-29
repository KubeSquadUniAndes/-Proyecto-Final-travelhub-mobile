package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.data.AuthRepository
import com.example.travelhubapp_mobile.data.AuthResult
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(onRegister: () -> Unit, onLogin: () -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var idNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(TokenManager(context)) }

    Column(Modifier.fillMaxSize().background(BluGradient).verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(16.dp))
        Box(Modifier.padding(start = 16.dp)) { THBackButton(onBack) }

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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp)).background(White).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            THInput(name, { name = it; errorMessage = null }, "Nombre completo", "Juan Pérez García", Icons.Default.Person)
            THInput(email, { email = it; errorMessage = null }, "Correo electrónico", "correo@ejemplo.com", Icons.Default.Email)
            THInput(phone, { phone = it; errorMessage = null }, "Teléfono móvil", "+57 300 123 4567", Icons.Default.Phone)
            THInput(idNumber, { idNumber = it; errorMessage = null }, "Número de identificación", "1234567890", Icons.Default.Badge)
            THInput(password, { password = it; errorMessage = null }, "Contraseña", "Mínimo 8 caracteres", Icons.Default.Lock, isPassword = true)
            THInput(confirmPassword, { confirmPassword = it; errorMessage = null }, "Confirmar contraseña", "Repite tu contraseña", Icons.Default.Lock, isPassword = true)

            if (errorMessage != null) {
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = {
                    when {
                        name.isBlank() || email.isBlank() || phone.isBlank() || idNumber.isBlank() || password.isBlank() -> {
                            errorMessage = "Completa todos los campos"; return@Button
                        }
                        password != confirmPassword -> {
                            errorMessage = "Las contraseñas no coinciden"; return@Button
                        }
                        password.length < 8 -> {
                            errorMessage = "La contraseña debe tener mínimo 8 caracteres"; return@Button
                        }
                    }
                    val parts = name.trim().split(" ", limit = 2)
                    scope.launch {
                        isLoading = true
                        errorMessage = null
                        when (val result = authRepository.register(
                            firstName = parts.first(),
                            lastName = if (parts.size > 1) parts[1] else "",
                            email = email,
                            phone = phone,
                            password = password,
                            identificationNumber = idNumber
                        )) {
                            is AuthResult.Success -> { showSuccess = true }
                            is AuthResult.Error -> errorMessage = result.message
                        }
                        isLoading = false
                    }
                },
                enabled = !isLoading,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                modifier = Modifier.fillMaxWidth().height(59.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Crear cuenta", style = MaterialTheme.typography.titleLarge, color = White)
                }
            }

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

    if (showSuccess) {
        AlertDialog(
            onDismissRequest = {},
            icon = { Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF00A63E), modifier = Modifier.size(48.dp)) },
            title = { Text("¡Cuenta creada!", style = MaterialTheme.typography.headlineMedium) },
            text = { Text("Tu cuenta se ha registrado exitosamente. Ahora puedes iniciar sesión.", style = MaterialTheme.typography.bodyLarge) },
            confirmButton = {
                Button(
                    onClick = { showSuccess = false; onRegister() },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) { Text("Ir a iniciar sesión") }
            }
        )
    }
}
