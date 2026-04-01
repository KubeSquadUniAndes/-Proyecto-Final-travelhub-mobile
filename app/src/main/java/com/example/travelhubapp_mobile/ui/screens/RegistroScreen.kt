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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.data.AuthRepository
import com.example.travelhubapp_mobile.data.AuthResult
import com.example.travelhubapp_mobile.data.TokenManager
import com.example.travelhubapp_mobile.ui.components.BluGradient
import com.example.travelhubapp_mobile.ui.components.THBackButton
import com.example.travelhubapp_mobile.ui.components.THDatePicker
import com.example.travelhubapp_mobile.ui.components.THDropdown
import com.example.travelhubapp_mobile.ui.components.THInput
import com.example.travelhubapp_mobile.ui.components.THLogo
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.White
import kotlinx.coroutines.launch

private const val MIN_PASSWORD_LENGTH = 8
private val SUCCESS_COLOR = Color(0xFF00A63E)

private val ID_TYPES = listOf(
    "CC" to "Cédula (CC)",
    "CE" to "Cédula Extranjería (CE)",
    "PA" to "Pasaporte (PA)",
    "TI" to "Tarjeta de Identidad (TI)"
)

@Composable
fun RegistroScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    onBack: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var idType by remember { mutableStateOf("Cédula (CC)") }
    var idNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepo = remember { AuthRepository(TokenManager(context)) }

    Column(
        Modifier.fillMaxSize().background(BluGradient)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(16.dp))
        Box(Modifier.padding(start = 16.dp)) { THBackButton(onBack) }
        RegistroHeader()
        RegistroFormCard(
            firstName, lastName, email, phone, country, city,
            birthDate, idType, idNumber, password, confirmPassword,
            isLoading, errorMessage,
            onClear = { errorMessage = null },
            onFirstName = { firstName = it },
            onLastName = { lastName = it },
            onEmail = { email = it },
            onPhone = { phone = it },
            onCountry = { country = it },
            onCity = { city = it },
            onBirthDate = { birthDate = it },
            onIdType = { idType = it },
            onIdNumber = { idNumber = it },
            onPassword = { password = it },
            onConfirm = { confirmPassword = it },
            onSubmit = {
                val err = validateAllFields(
                    firstName, lastName, email, phone,
                    country, city, birthDate, idNumber,
                    password, confirmPassword
                )
                if (err != null) { errorMessage = err; return@RegistroFormCard }
                val idCode = ID_TYPES.find { it.second == idType }?.first ?: "CC"
                scope.launch {
                    isLoading = true; errorMessage = null
                    when (val r = authRepo.register(
                        firstName, lastName, email, phone, password,
                        country, city, birthDate, idCode, idNumber
                    )) {
                        is AuthResult.Success -> showSuccess = true
                        is AuthResult.Error -> errorMessage = r.message
                    }
                    isLoading = false
                }
            },
            onLogin = onLogin
        )
        Spacer(Modifier.height(24.dp))
    }

    if (showSuccess) { SuccessDialog { showSuccess = false; onRegister() } }
}

@Suppress("CyclomaticComplexMethod")
private fun validateAllFields(
    firstName: String, lastName: String, email: String,
    phone: String, country: String, city: String,
    birthDate: String, idNumber: String,
    password: String, confirmPassword: String
): String? = when {
    firstName.isBlank() || lastName.isBlank() || email.isBlank()
        || phone.isBlank() || country.isBlank() || city.isBlank()
        || birthDate.isBlank() || idNumber.isBlank()
        || password.isBlank() -> "Completa todos los campos"
    password != confirmPassword -> "Las contraseñas no coinciden"
    password.length < MIN_PASSWORD_LENGTH ->
        "La contraseña debe tener mínimo $MIN_PASSWORD_LENGTH caracteres"
    else -> null
}

@Composable
private fun RegistroHeader() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        THLogo()
        Text("Crear cuenta", style = MaterialTheme.typography.displayLarge, color = White)
        Text(
            "Completa tus datos para continuar",
            style = MaterialTheme.typography.bodyLarge, color = Blue100
        )
    }
}

@Suppress("LongParameterList", "LongMethod")
@Composable
private fun RegistroFormCard(
    firstName: String, lastName: String, email: String,
    phone: String, country: String, city: String,
    birthDate: String, idType: String, idNumber: String,
    password: String, confirmPassword: String,
    isLoading: Boolean, errorMessage: String?,
    onClear: () -> Unit,
    onFirstName: (String) -> Unit, onLastName: (String) -> Unit,
    onEmail: (String) -> Unit, onPhone: (String) -> Unit,
    onCountry: (String) -> Unit, onCity: (String) -> Unit,
    onBirthDate: (String) -> Unit, onIdType: (String) -> Unit,
    onIdNumber: (String) -> Unit, onPassword: (String) -> Unit,
    onConfirm: (String) -> Unit,
    onSubmit: () -> Unit, onLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)).background(White).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Información personal",
            style = MaterialTheme.typography.headlineSmall
        )
        PersonalInfoFields(
            firstName, lastName, email, phone, onClear,
            onFirstName, onLastName, onEmail, onPhone
        )
        LocationFields(country, city, birthDate, onClear, onCountry, onCity, onBirthDate)
        IdFields(idType, idNumber, onClear, onIdType, onIdNumber)
        PasswordFields(password, confirmPassword, onClear, onPassword, onConfirm)
        ErrorMessage(errorMessage)
        Spacer(Modifier.height(4.dp))
        SubmitButton(isLoading, onSubmit)
        LoginLink(onLogin)
    }
}

@Suppress("LongParameterList")
@Composable
private fun PersonalInfoFields(
    firstName: String, lastName: String, email: String, phone: String,
    onClear: () -> Unit,
    onFirstName: (String) -> Unit, onLastName: (String) -> Unit,
    onEmail: (String) -> Unit, onPhone: (String) -> Unit
) {
    THInput(firstName, { onClear(); onFirstName(it) },
        "Nombre *", "Juan", Icons.Default.Person)
    THInput(lastName, { onClear(); onLastName(it) },
        "Apellido *", "Pérez", Icons.Default.Person)
    THInput(email, { onClear(); onEmail(it) },
        "Email *", "tu@email.com", Icons.Default.Email)
    THInput(phone, { onClear(); onPhone(it) },
        "Teléfono *", "+57 300 123 4567", Icons.Default.Phone)
}

@Suppress("LongParameterList")
@Composable
private fun LocationFields(
    country: String, city: String, birthDate: String,
    onClear: () -> Unit,
    onCountry: (String) -> Unit, onCity: (String) -> Unit,
    onBirthDate: (String) -> Unit
) {
    THInput(country, { onClear(); onCountry(it) },
        "País *", "Colombia", Icons.Default.Public)
    THInput(city, { onClear(); onCity(it) },
        "Ciudad *", "Bogotá", Icons.Default.LocationCity)
    THDatePicker(birthDate, { onClear(); onBirthDate(it) },
        "Fecha de nacimiento *")
}

@Composable
private fun IdFields(
    idType: String, idNumber: String, onClear: () -> Unit,
    onIdType: (String) -> Unit, onIdNumber: (String) -> Unit
) {
    THDropdown(
        idType, { onClear(); onIdType(it) },
        "Tipo de ID *",
        ID_TYPES.map { it.second },
        Icons.Default.Badge
    )
    THInput(idNumber, { onClear(); onIdNumber(it) },
        "Número de ID *", "1234567890", Icons.Default.Badge)
}

@Composable
private fun PasswordFields(
    password: String, confirmPassword: String, onClear: () -> Unit,
    onPassword: (String) -> Unit, onConfirm: (String) -> Unit
) {
    THInput(password, { onClear(); onPassword(it) },
        "Contraseña *", "Mínimo 8 caracteres", Icons.Default.Lock, isPassword = true)
    THInput(confirmPassword, { onClear(); onConfirm(it) },
        "Confirmar contraseña *", "Repite tu contraseña", Icons.Default.Lock, isPassword = true)
}

@Composable
private fun ErrorMessage(errorMessage: String?) {
    if (errorMessage != null) {
        Text(
            errorMessage, color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun SubmitButton(isLoading: Boolean, onSubmit: () -> Unit) {
    Button(
        onClick = onSubmit, enabled = !isLoading,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
        modifier = Modifier.fillMaxWidth().height(59.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp
            )
        } else {
            Text("Crear cuenta", style = MaterialTheme.typography.titleLarge, color = White)
        }
    }
}

@Composable
private fun LoginLink(onLogin: () -> Unit) {
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

@Composable
private fun SuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        icon = {
            Icon(
                Icons.Default.CheckCircle, null,
                tint = SUCCESS_COLOR, modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text("¡Cuenta creada!", style = MaterialTheme.typography.headlineMedium)
        },
        text = {
            Text(
                "Tu cuenta se ha registrado exitosamente. Ahora puedes iniciar sesión.",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Blue600)
            ) { Text("Ir a iniciar sesión") }
        }
    )
}
