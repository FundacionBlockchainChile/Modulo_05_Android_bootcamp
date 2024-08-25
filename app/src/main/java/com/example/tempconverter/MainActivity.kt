package com.example.tempconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import com.example.tempconverter.ui.theme.TempConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TempConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TempConverterApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempConverterApp(modifier: Modifier = Modifier) {
    var temperature by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // Estableciendo una paleta de colores más profesional
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            onPrimary = Color.White,
            onSecondary = Color.Black
        )
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = temperature,
                onValueChange = { temperature = it },
                label = { Text("Enter temperature in Celsius:") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    result = convertTemperature(temperature)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Convert", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                result,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

fun convertTemperature(input: String): String {
    return try {
        val temp = input.toDouble()
        val celsiusToFahrenheit = temp * 9/5 + 32
        val fahrenheitToCelsius = (temp - 32) * 5/9
        val celsiusToKelvin = temp + 273.15
        "Celsius to Fahrenheit: ${String.format("%.2f", celsiusToFahrenheit)}\n" +
                "Fahrenheit to Celsius: ${String.format("%.2f", fahrenheitToCelsius)}\n" +
                "Celsius to Kelvin: ${String.format("%.2f", celsiusToKelvin)}"
    } catch (e: Exception) {
        "Invalid input"
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TempConverterTheme {
        TempConverterApp()
    }
}

