package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.data.AuthRepository
import com.example.travelhubapp_mobile.data.AuthResult
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(TokenManager(context)) }

    Column(
        modifier = Modifier.fillMaxSize().background(BluGradient).verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))
        Box(Modifier.padding(start = 16.dp)) { THBackButton(onBack) }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            THLogo()
            Text("TravelHub", style = MaterialTheme.typography.displayLarge, color = White)
            Text("Bienvenido de nuevo", style = MaterialTheme.typography.bodyLarge, color = Blue100)
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(16.dp)).background(White).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            THInput(email, { email = it; errorMessage = null }, "Correo electrónico", "correo@ejemplo.com", Icons.Default.Email)
            THInput(password, { password = it; errorMessage = null }, "Contraseña", "••••••••", Icons.Default.Lock, isPassword = true)

            if (errorMessage != null) {
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            TextButton(onClick = {}) {
                Text("¿Olvidaste tu contraseña?", color = Blue600, style = MaterialTheme.typography.titleSmall)
            }

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Completa todos los campos"
                        return@Button
                    }
                    scope.launch {
                        isLoading = true
                        errorMessage = null
                        when (val result = authRepository.login(email, password)) {
                            is AuthResult.Success -> onLogin()
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
                    Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge, color = White)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(Modifier.weight(1f), color = Gray300.copy(alpha = 0.5f))
            Text("  o regístrate aquí  ", style = MaterialTheme.typography.bodyMedium, color = White.copy(alpha = 0.7f))
            HorizontalDivider(Modifier.weight(1f), color = Gray300.copy(alpha = 0.5f))
        }

        TextButton(onClick = onRegister, modifier = Modifier.fillMaxWidth()) {
            Text("Crear cuenta nueva", color = White, style = MaterialTheme.typography.titleMedium)
        }

        Text(
            "Al continuar, aceptas nuestros Términos de servicio y Política de privacidad",
            style = MaterialTheme.typography.bodySmall,
            color = White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 16.dp)
        )
    }
}
