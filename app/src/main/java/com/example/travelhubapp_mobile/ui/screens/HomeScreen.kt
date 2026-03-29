package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.data.AuthRepository
import com.example.travelhubapp_mobile.data.AuthResult
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.network.ProfileResponse
import com.example.travelhubapp_mobile.ui.components.BluGradient
import com.example.travelhubapp_mobile.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    var profile by remember { mutableStateOf<ProfileResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(TokenManager(context)) }

    LaunchedEffect(Unit) {
        when (val result = authRepository.getProfile()) {
            is AuthResult.Success -> profile = result.data
            is AuthResult.Error -> error = result.message
        }
        isLoading = false
    }

    Column(Modifier.fillMaxSize()) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth().background(BluGradient).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(Modifier.size(80.dp).clip(CircleShape).background(White.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, null, tint = White, modifier = Modifier.size(40.dp))
            }
            if (profile != null) {
                Text(profile!!.fullName ?: "${profile!!.firstName ?: ""} ${profile!!.lastName ?: ""}", style = MaterialTheme.typography.headlineLarge, color = White)
                Text(profile!!.email ?: "", style = MaterialTheme.typography.bodyMedium, color = Blue100)
            } else if (isLoading) {
                CircularProgressIndicator(color = White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().background(Gray50).verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }

            if (profile != null) {
                // Success card
                Card(shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(containerColor = White)) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Default.CheckCircle, null, tint = Success, modifier = Modifier.size(32.dp))
                        Column {
                            Text("Sesión activa", style = MaterialTheme.typography.titleMedium)
                            Text("Conectado al backend de TravelHub", style = MaterialTheme.typography.bodySmall, color = Gray500)
                        }
                    }
                }

                // Profile info
                Card(shape = MaterialTheme.shapes.medium, colors = CardDefaults.cardColors(containerColor = White)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Mi perfil", style = MaterialTheme.typography.headlineSmall)
                        ProfileRow("Nombre", profile!!.fullName ?: "${profile!!.firstName ?: "-"} ${profile!!.lastName ?: "-"}")
                        ProfileRow("Email", profile!!.email ?: "-")
                        ProfileRow("Estado", profile!!.status ?: "-")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            OutlinedButton(
                onClick = {
                    scope.launch {
                        authRepository.logout()
                        onLogout()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, null, modifier = Modifier.size(19.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Gray500)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}
