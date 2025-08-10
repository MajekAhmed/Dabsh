package com.majek.dabsh.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("العربية") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "⚙️ الإعدادات",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("الوضع الداكن", fontSize = 18.sp)
            Switch(checked = isDarkTheme, onCheckedChange = {
                isDarkTheme = it
                // TODO: احفظ الإعداد في DataStore أو SharedPreferences
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("اللغة", fontSize = 18.sp)
            DropdownMenuBox(language) { selected ->
                language = selected
                // TODO: حفظ اختيار اللغة لاحقًا
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // TODO: Reset settings
                isDarkTheme = false
                language = "العربية"
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("إعادة الإعدادات", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("privacy") },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline)
        ) {
            Text("سياسة الخصوصية", fontSize = 16.sp)
        }
    }
}

@Composable
fun DropdownMenuBox(currentSelection: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(currentSelection)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("العربية") }, onClick = {
                onSelect("العربية")
                expanded = false
            })
            DropdownMenuItem(text = { Text("English") }, onClick = {
                onSelect("English")
                expanded = false
            })
        }
    }
}
