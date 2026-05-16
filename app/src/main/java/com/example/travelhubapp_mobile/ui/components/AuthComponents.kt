package com.example.travelhubapp_mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.theme.Blue100
import com.example.travelhubapp_mobile.ui.theme.Blue600
import com.example.travelhubapp_mobile.ui.theme.Blue900
import com.example.travelhubapp_mobile.ui.theme.Gray200
import com.example.travelhubapp_mobile.ui.theme.Gray300
import com.example.travelhubapp_mobile.ui.theme.Gray400
import com.example.travelhubapp_mobile.ui.theme.Gray600
import com.example.travelhubapp_mobile.ui.theme.Gray700
import com.example.travelhubapp_mobile.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

val BluGradient = Brush.linearGradient(listOf(Blue600, Blue900))
val CardShape = RoundedCornerShape(10.dp)

@Composable
fun THLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(79.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Text("TH", style = MaterialTheme.typography.displayLarge, color = Blue600)
    }
}

@Composable
fun THInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    var visible by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.titleSmall, color = Gray700)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Gray400) },
            leadingIcon = leadingIcon?.let {
                { Icon(it, null, tint = Gray400, modifier = Modifier.size(19.dp)) }
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { visible = !visible }) {
                        val icon = if (visible) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility
                        Icon(icon, "Toggle password visibility", tint = Gray400, modifier = Modifier.size(19.dp))
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !visible)
                PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else keyboardType
            ),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Gray300, focusedBorderColor = Blue600
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().height(50.dp).then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
        )
    }
}

@Composable
fun THDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
    leadingIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.titleSmall, color = Gray700)
        Box {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                leadingIcon = leadingIcon?.let {
                    { Icon(it, null, tint = Gray400, modifier = Modifier.size(19.dp)) }
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown, null,
                        tint = Gray400,
                        modifier = Modifier.clickable { expanded = true }
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Gray300, focusedBorderColor = Blue600
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { expanded = true }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = { onValueChange(option); expanded = false }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun THDatePicker(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "Selecciona fecha",
    minDate: Long? = null,
    maxDate: Long? = null,
    initialDate: Long? = null,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    var showDialog by remember { mutableStateOf(false) }

    val formatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).apply { timeZone = TimeZone.getTimeZone("UTC") } }
    val displayFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale("es", "CO")).apply { timeZone = TimeZone.getTimeZone("UTC") } }

    val initialMillis = remember(value, initialDate) {
        if (value.isNotBlank()) {
            try { formatter.parse(value)?.time } catch (_: Exception) { null }
        } else initialDate
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val afterMin = minDate == null || utcTimeMillis >= minDate
                    val beforeMax = maxDate == null || utcTimeMillis <= maxDate
                    return afterMin && beforeMax
                }
        }
    )

    LaunchedEffect(value) {
        if (value.isBlank()) {
            datePickerState.selectedDateMillis = null
        }
    }

    val displayValue = if (value.isNotBlank()) {
        try { displayFormatter.format(formatter.parse(value)!!) } catch (_: Exception) { value }
    } else ""

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onValueChange(formatter.format(it))
                        }
                        showDialog = false
                    }
                ) { Text("Aceptar", color = Blue600) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar", color = Gray600)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = White,
                titleContentColor = Blue600,
                headlineContentColor = Blue600,
                weekdayContentColor = Gray600,
                subheadContentColor = Gray600,
                navigationContentColor = Blue600,
                yearContentColor = Gray700,
                currentYearContentColor = Blue600,
                selectedYearContentColor = White,
                selectedYearContainerColor = Blue600,
                dayContentColor = Gray700,
                selectedDayContentColor = White,
                selectedDayContainerColor = Blue600,
                todayContentColor = Blue600,
                todayDateBorderColor = Blue600,
                dayInSelectionRangeContentColor = White,
                dayInSelectionRangeContainerColor = Blue100
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = White,
                    titleContentColor = Blue600,
                    headlineContentColor = Blue600,
                    weekdayContentColor = Gray600,
                    subheadContentColor = Gray600,
                    navigationContentColor = Blue600,
                    yearContentColor = Gray700,
                    currentYearContentColor = Blue600,
                    selectedYearContentColor = White,
                    selectedYearContainerColor = Blue600,
                    dayContentColor = Gray700,
                    selectedDayContentColor = White,
                    selectedDayContainerColor = Blue600,
                    todayContentColor = Blue600,
                    todayDateBorderColor = Blue600
                ),
                title = {
                    Text(
                        label,
                        style = MaterialTheme.typography.titleMedium,
                        color = Blue600,
                        modifier = Modifier.padding(start = 24.dp, top = 16.dp)
                    )
                }
            )
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.titleSmall, color = Gray700)
        Box(modifier = if (testTag != null) Modifier.testTag(testTag) else Modifier) {
            OutlinedTextField(
                value = displayValue,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                placeholder = { Text(placeholder, color = Gray400) },
                leadingIcon = {
                    val iconTint = if (value.isNotBlank()) Blue600 else Gray400
                    Icon(
                        Icons.Default.CalendarMonth,
                        null,
                        tint = iconTint,
                        modifier = Modifier.size(19.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = if (value.isNotBlank()) Blue600 else Gray300,
                    disabledTextColor = Gray700,
                    disabledPlaceholderColor = Gray400,
                    disabledLeadingIconColor = if (value.isNotBlank()) Blue600 else Gray400
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            )
            Box(modifier = Modifier.matchParentSize().clickable { showDialog = true })
        }
    }
}

@Composable
fun THButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
        modifier = modifier.fillMaxWidth().height(59.dp)
    ) {
        Text(text, style = MaterialTheme.typography.titleLarge, color = White)
    }
}

@Composable
fun THBackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(43.dp)
            .clip(CircleShape)
            .background(White.copy(alpha = 0.2f))
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack, "Back",
            tint = White, modifier = Modifier.size(23.dp)
        )
    }
}

@Composable
fun THBottomBar(selected: Int, onSelect: (Int) -> Unit, onLogout: () -> Unit) {
    NavigationBar(
        containerColor = White,
        tonalElevation = 0.dp,
        modifier = Modifier.border(0.5.dp, Gray200)
    ) {
        NavigationBarItem(
            selected = selected == 0,
            onClick = { onSelect(0) },
            icon = { Icon(Icons.Default.Home, "Inicio", modifier = Modifier.size(23.dp)) },
            label = { Text("Inicio", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Blue600, selectedTextColor = Blue600,
                unselectedIconColor = Gray600, unselectedTextColor = Gray600,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selected == 1,
            onClick = { onSelect(1) },
            icon = { Icon(Icons.Default.Favorite, "Mis reservas", modifier = Modifier.size(23.dp)) },
            label = { Text("Reservas", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Blue600, selectedTextColor = Blue600,
                unselectedIconColor = Gray600, unselectedTextColor = Gray600,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selected == 2,
            onClick = { onSelect(2) },
            icon = { Icon(Icons.Default.Person, "Perfil", modifier = Modifier.size(23.dp)) },
            label = { Text("Perfil", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Blue600, selectedTextColor = Blue600,
                unselectedIconColor = Gray600, unselectedTextColor = Gray600,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = onLogout,
            icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, "Cerrar sesión", modifier = Modifier.size(23.dp)) },
            label = { Text("Salir", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Blue600, selectedTextColor = Blue600,
                unselectedIconColor = Gray600, unselectedTextColor = Gray600,
                indicatorColor = Color.Transparent
            )
        )
    }
}
