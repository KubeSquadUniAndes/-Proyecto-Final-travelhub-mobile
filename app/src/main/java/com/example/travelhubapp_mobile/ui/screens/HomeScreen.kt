package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.data.Hospedaje
import com.example.travelhubapp_mobile.data.HospedajeRepository
import com.example.travelhubapp_mobile.ui.components.HospedajeCard
import com.example.travelhubapp_mobile.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(onReservar: (Hospedaje) -> Unit = {}, modifier: Modifier = Modifier) {
    var hospedajes by remember { mutableStateOf<List<Hospedaje>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            hospedajes = HospedajeRepository.getHospedajes()
            isLoading = false
        }
    }

    Column(modifier.fillMaxSize().background(White)) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("TravelHub", style = MaterialTheme.typography.headlineLarge, color = Blue600)
            Text("Encuentra tu hotel ideal", style = MaterialTheme.typography.displayLarge, color = Gray900)
            Text(
                "Miles de opciones de alojamiento al mejor precio",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray600
            )
        }

        // Content
        if (isLoading) {
            // Loading indicator (CA: Indicador de carga)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    CircularProgressIndicator(color = Blue600, strokeWidth = 3.dp)
                    Text("Cargando hospedajes...", style = MaterialTheme.typography.bodyMedium, color = Gray500)
                }
            }
        } else {
            // Lista de hospedajes
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Gray50),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "${hospedajes.size} hospedajes disponibles",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray500
                    )
                }
                items(hospedajes, key = { it.id }) { hospedaje ->
                    HospedajeCard(hospedaje = hospedaje, onReservar = onReservar)
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}
