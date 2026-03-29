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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Gray50
import com.example.travelhubapp_mobile.ui.theme.Gray500
import com.example.travelhubapp_mobile.ui.theme.Success
import com.example.travelhubapp_mobile.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    var profile by remember { mutableStateOf<ProfileResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepo = remember { AuthRepository(TokenManager(context)) }

    LaunchedEffect(Unit) {
        when (val result = authRepo.getProfile()) {
            is AuthResult.Success -> profile = result.data
            is AuthResult.Error -> error = result.message
        }
        isLoading = false
    }

    Column(Modifier.fillMaxSize()) {
        HomeHeader(profile, isLoading)
        HomeContent(profile, error, scope, authRepo, onLogout)
    }
}

@Composable
private fun HomeHeader(profile: ProfileResponse?, isLoading: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth().background(BluGradient).padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            Modifier.size(80.dp).clip(CircleShape)
                .background(White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, null, tint = White, modifier = Modifier.size(40.dp))
        }
        if (profile != null) {
            val displayName = profile.fullName
                ?: "${profile.firstName.orEmpty()} ${profile.lastName.orEmpty()}"
            Text(displayName, style = MaterialTheme.typography.headlineLarge, color = White)
            Text(
                profile.email.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = Blue100
            )
        } else if (isLoading) {
            CircularProgressIndicator(
                color = White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp
            )
        }
    }
}

@Suppress("LongParameterList")
@Composable
private fun HomeContent(
    profile: ProfileResponse?,
    error: String?,
    scope: kotlinx.coroutines.CoroutineScope,
    authRepo: AuthRepository,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Gray50)
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (error != null) {
            Text(
                error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (profile != null) {
            SessionCard()
            ProfileCard(profile)
        }
        Spacer(Modifier.height(24.dp))
        LogoutButton(scope, authRepo, onLogout)
    }
}

@Composable
private fun SessionCard() {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.CheckCircle, null, tint = Success, modifier = Modifier.size(32.dp))
            Column {
                Text("Sesión activa", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Conectado al backend de TravelHub",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray500
                )
            }
        }
    }
}

@Composable
private fun ProfileCard(profile: ProfileResponse) {
    val displayName = profile.fullName
        ?: "${profile.firstName.orEmpty()} ${profile.lastName.orEmpty()}"
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Mi perfil", style = MaterialTheme.typography.headlineSmall)
            ProfileRow("Nombre", displayName)
            ProfileRow("Email", profile.email ?: "-")
            ProfileRow("Estado", profile.status ?: "-")
        }
    }
}

@Composable
private fun LogoutButton(
    scope: kotlinx.coroutines.CoroutineScope,
    authRepo: AuthRepository,
    onLogout: () -> Unit
) {
    OutlinedButton(
        onClick = {
            scope.launch {
                authRepo.logout()
                onLogout()
            }
        },
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Icon(Icons.AutoMirrored.Filled.Logout, null, modifier = Modifier.size(19.dp))
        Spacer(Modifier.width(8.dp))
        Text("Cerrar sesión", style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Gray500)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}
