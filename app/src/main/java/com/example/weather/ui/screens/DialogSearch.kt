package com.example.weather.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.weather.ui.viewmodel.WeatherViewModel

@Composable
fun DialogSearch(viewModel: WeatherViewModel, onSubmit: (String) -> Unit) {
    val dialogText = remember {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = {
            viewModel.hideDialog()
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.hideDialog()
                onSubmit(dialogText.value)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.hideDialog() }) {
                Text("CANCEL")
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enter name city:")
                TextField(value = dialogText.value, onValueChange = { str ->
                    dialogText.value = str
                })
            }
        }
    )
}