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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.*
import com.example.travelhubapp_mobile.ui.theme.*
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(
    onReservar: () -> Unit,
    onPerfil: () -> Unit,
    onMisReservas: () -> Unit,
    onLogout: () -> Unit,
    viewModel: HotelViewModel
) {
    Scaffold(
        bottomBar = {
            THBottomBar(
                selected = 0,
                onSelect = { 
                    when(it) {
                        1 -> onMisReservas()
                        2 -> onPerfil()
                    }
                },
                onLogout = onLogout
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("TravelHub", style = MaterialTheme.typography.headlineLarge, color = Blue600)
            }

            // Hero
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Encuentra tu hotel ideal", style = MaterialTheme.typography.displayLarge, color = Gray900)
                Text("Miles de opciones de alojamiento al mejor precio", style = MaterialTheme.typography.bodyLarge, color = Gray600)
            }

            // Search Form Card
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp).background(White)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                THInput(
                    viewModel.destino, { viewModel.destino = it }, "Destino", "¿A dónde viajas?", Icons.Default.LocationOn,
                    testTag = "input_destino"
                )
                
                val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.ROOT) }
                val checkInTime = remember(viewModel.checkIn) {
                    try { dateFormatter.parse(viewModel.checkIn.substringBefore('T'))?.time } catch (_: Exception) { null }
                }
                val checkOutTime = remember(viewModel.checkOut) {
                    try { dateFormatter.parse(viewModel.checkOut.substringBefore('T'))?.time } catch (_: Exception) { null }
                }

                THDatePicker(
                    value = viewModel.checkIn.substringBefore('T'),
                    onValueChange = { viewModel.checkIn = "${it}T12:00:00" },
                    label = "Check-in",
                    placeholder = "Selecciona fecha",
                    minDate = System.currentTimeMillis(),
                    maxDate = checkOutTime,
                    testTag = "picker_checkin"
                )

                THDatePicker(
                    value = viewModel.checkOut.substringBefore('T'),
                    onValueChange = { viewModel.checkOut = "${it}T12:00:00" },
                    label = "Check-out",
                    placeholder = "Selecciona fecha",
                    minDate = checkInTime ?: System.currentTimeMillis(),
                    testTag = "picker_checkout"
                )

                THInput(
                    viewModel.guests,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty()) {
                            viewModel.guests = ""
                        } else if (newValue.all { it.isDigit() } && (newValue.toIntOrNull() ?: 0) <= 20) {
                            viewModel.guests = newValue
                        }
                    },
                    "Huéspedes",
                    "Máximo 20",
                    Icons.Default.People,
                    keyboardType = KeyboardType.Number,
                    testTag = "input_guests"
                )

                if (viewModel.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    THButton("Buscar hoteles", onClick = {
                        viewModel.searchRooms(onSuccess = onReservar)
                    }, modifier = Modifier.testTag("btn_search"))
                }

                viewModel.error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(Modifier.height(80.dp))
        }
    }
}
