package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.THButton
import com.example.travelhubapp_mobile.ui.components.THInput
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Gray200
import com.example.travelhubapp_mobile.ui.theme.Gray50
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.White
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(onBack: () -> Unit, onSuccess: () -> Unit, viewModel: HotelViewModel) {
    val booking = viewModel.selectedBookingForPayment

    var cardNumber by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Pagar reserva",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
                )
                HorizontalDivider(color = Gray200, thickness = 0.5.dp)
            }
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp, shadowElevation = 8.dp, color = White) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total a pagar:", style = MaterialTheme.typography.bodyLarge, color = Gray600)
                        Text(
                            "COP ${booking?.finalPrice ?: booking?.totalPrice ?: "0"}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Blue600,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.testTag("pago_total")
                        )
                    }
                    THButton(
                        text = if (viewModel.isLoading) "Procesando..." else "Confirmar pago",
                        onClick = {
                            val bookingId = booking?.id ?: return@THButton
                            val amount = booking.finalPrice?.toDoubleOrNull()
                                ?: booking.totalPrice?.toDoubleOrNull()
                                ?: 0.0
                            val lastFour = cardNumber.takeLast(4).ifBlank { "0000" }
                            when {
                                cardNumber.length < 16 -> viewModel.error = "Ingresa un número de tarjeta válido (16 dígitos)"
                                cardName.isBlank() -> viewModel.error = "El nombre en la tarjeta es requerido"
                                email.isBlank() || !email.contains("@") -> viewModel.error = "Ingresa un email válido"
                                expiry.isBlank() -> viewModel.error = "La fecha de vencimiento es requerida"
                                cvv.length < 3 -> viewModel.error = "El CVV debe tener al menos 3 dígitos"
                                else -> viewModel.createPayment(
                                    bookingId = bookingId,
                                    amount = amount,
                                    cardLastFour = lastFour,
                                    cardholderName = cardName,
                                    cardholderEmail = email,
                                    onSuccess = onSuccess
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().testTag("btn_confirmar_pago")
                    )
                    viewModel.error?.let {
                        Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        },
        containerColor = Gray50
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resumen reserva
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Resumen de reserva", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    HorizontalDivider(color = Gray200)
                    PagoResumenItem("Código:", booking?.bookingCode ?: "-")
                    PagoResumenItem("Tipo:", booking?.roomType?.replaceFirstChar { it.uppercase() } ?: "-")
                    PagoResumenItem("Noches:", "${booking?.totalNights ?: "-"}")
                    PagoResumenItem("Estado:", booking?.statusDisplay ?: booking?.status ?: "-")
                }
            }

            // Datos de pago
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.CreditCard, null, tint = Blue600)
                        Text("Datos de pago", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    THInput(
                        value = cardNumber,
                        onValueChange = { if (it.length <= 16 && it.all { c -> c.isDigit() }) cardNumber = it },
                        label = "Número de tarjeta",
                        placeholder = "1234 5678 9012 3456",
                        testTag = "input_card_number"
                    )
                    THInput(
                        value = cardName,
                        onValueChange = { cardName = it },
                        label = "Nombre en la tarjeta",
                        placeholder = "JUAN PEREZ",
                        testTag = "input_card_name"
                    )
                    THInput(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "correo@ejemplo.com",
                        testTag = "input_pago_email"
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            THInput(value = expiry, onValueChange = { expiry = it }, label = "Vencimiento", placeholder = "MM/AA")
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            THInput(value = cvv, onValueChange = { cvv = it }, label = "CVV", placeholder = "123")
                        }
                    }
                }
            }

            // Seguridad
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Gray50),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Lock, null, tint = Blue600, modifier = Modifier.size(16.dp))
                    Text(
                        "Pago seguro con encriptación SSL",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray600
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PagoResumenItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Gray600, style = MaterialTheme.typography.bodyMedium)
        Text(value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
    }
}
