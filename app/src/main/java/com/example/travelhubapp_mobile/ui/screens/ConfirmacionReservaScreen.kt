package com.example.travelhubapp_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.components.THButton
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Blue900
import com.example.travelhubapp_mobile.ui.theme.Gray100
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.Gray900
import com.example.travelhubapp_mobile.ui.theme.White
import com.example.travelhubapp_mobile.ui.viewmodels.HotelViewModel

@Composable
fun ConfirmacionReservaScreen(onHome: () -> Unit, viewModel: HotelViewModel) {
    val booking = viewModel.lastBooking
    val bookingCode = booking?.bookingCode ?: "123456"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success Icon
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFF00C853)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¡Reserva confirmada!",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = Gray900,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Tu reserva ha sido procesada exitosamente",
            style = MaterialTheme.typography.bodyLarge,
            color = Gray600,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Booking Code Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Número de reserva",
                    style = MaterialTheme.typography.labelLarge,
                    color = Gray600
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "#${bookingCode}",
                    style = MaterialTheme.typography.displaySmall,
                    color = Blue600,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Gray100, thickness = 1.dp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Info box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Blue100)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(40.dp)
                        .background(Blue600)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Confirmación enviada: Hemos enviado los detalles de tu reserva a tu correo electrónico",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Blue900
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        THButton(
            text = "Volver al inicio",
            onClick = onHome,
            modifier = Modifier.fillMaxWidth().testTag("btn_back_home")
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "¿Necesitas ayuda?",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray600
            )
            Text(
                text = "+57 300 123 4567",
                style = MaterialTheme.typography.bodyLarge,
                color = Blue600,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
