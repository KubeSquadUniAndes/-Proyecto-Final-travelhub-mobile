package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.THBottomBar
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Gray50
import com.example.travelhubapp_mobile.ui.theme.Gray500
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.Gray900
import com.example.travelhubapp_mobile.ui.theme.StarYellow
import com.example.travelhubapp_mobile.ui.theme.White
import kotlinx.coroutines.delay

private data class Hospedaje(
    val id: Int,
    val nombre: String,
    val ubicacion: String,
    val precioPorNoche: String,
    val rating: String
)

private val hospedajes = listOf(
    Hospedaje(1, "Hotel Grand Luxury", "Bogotá, Colombia", "COP 600,000", "4.8"),
    Hospedaje(2, "Modern Boutique Hotel", "Medellín, Colombia", "COP 480,000", "4.6"),
    Hospedaje(3, "Beachfront Paradise Resort", "Cartagena, Colombia", "COP 800,000", "4.9"),
    Hospedaje(4, "Mountain View Lodge", "Santa Marta, Colombia", "COP 350,000", "4.5"),
    Hospedaje(5, "Pool View Resort", "San Andrés, Colombia", "COP 720,000", "4.7"),
)

private const val LOADING_DELAY = 1500L

@Composable
fun HomeScreen(onReservar: () -> Unit = {}, onPerfil: () -> Unit = {}, onLogout: () -> Unit = {}) {
    var isLoading by remember { mutableStateOf(true) }
    var lista by remember { mutableStateOf<List<Hospedaje>>(emptyList()) }
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        delay(LOADING_DELAY)
        lista = hospedajes
        isLoading = false
    }

    Scaffold(
        bottomBar = {
            THBottomBar(
                selected = selectedTab,
                onSelect = { tab -> selectedTab = tab; if (tab == 1) onPerfil() },
                onLogout = onLogout
            )
        }
    ) { innerPadding ->
        HomeContent(isLoading, lista, onReservar, Modifier.padding(innerPadding))
    }
}

@Composable
private fun HomeContent(
    isLoading: Boolean,
    lista: List<Hospedaje>,
    onReservar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize().background(White)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("TravelHub", style = MaterialTheme.typography.headlineLarge, color = Blue600)
            Text("Hospedajes disponibles", style = MaterialTheme.typography.titleMedium, color = Gray600)
        }
        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = Blue600, strokeWidth = 3.dp)
                    Text("Cargando hospedajes...", style = MaterialTheme.typography.bodyMedium, color = Gray500)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Gray50),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "${lista.size} hospedajes disponibles",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray500
                    )
                }
                items(lista, key = { it.id }) { hospedaje -> HospedajeCard(hospedaje, onReservar) }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun HospedajeCard(hospedaje: Hospedaje, onReservar: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(180.dp).background(Blue100),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Hotel, null, tint = Blue600, modifier = Modifier.size(48.dp))
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(hospedaje.nombre, style = MaterialTheme.typography.titleLarge, color = Gray900)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.LocationOn, null, tint = Gray500, modifier = Modifier.size(16.dp))
                    Text(hospedaje.ubicacion, style = MaterialTheme.typography.bodyMedium, color = Gray600)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.Star, null, tint = StarYellow, modifier = Modifier.size(15.dp))
                    Text(hospedaje.rating, style = MaterialTheme.typography.titleSmall, color = Gray900)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(hospedaje.precioPorNoche, style = MaterialTheme.typography.headlineLarge, color = Blue600)
                        Text("por noche", style = MaterialTheme.typography.bodySmall, color = Gray500)
                    }
                    Button(
                        onClick = onReservar,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text("Reservar", style = MaterialTheme.typography.labelMedium, color = White)
                    }
                }
            }
        }
    }
}
