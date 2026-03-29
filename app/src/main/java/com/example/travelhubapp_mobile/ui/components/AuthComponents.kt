package com.example.travelhubapp_mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.travelhubapp_mobile.ui.theme.*

val BluGradient = Brush.linearGradient(listOf(Blue600, Blue900))

@Composable
fun THLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(79.dp).clip(RoundedCornerShape(16.dp)).background(White),
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
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.titleSmall, color = Gray700)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Gray400) },
            leadingIcon = leadingIcon?.let { { Icon(it, null, tint = Gray400, modifier = Modifier.size(19.dp)) } },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { visible = !visible }) {
                        Icon(if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility, null, tint = Gray400, modifier = Modifier.size(19.dp))
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray300, focusedBorderColor = Blue600),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        )
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
        modifier = Modifier.size(43.dp).clip(CircleShape).background(White.copy(alpha = 0.2f))
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = White, modifier = Modifier.size(23.dp))
    }
}
