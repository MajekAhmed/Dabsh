package com.majek.dabsh.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.majek.dabsh.viewmodel.GameViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: GameViewModel) {
    val focusManager = LocalFocusManager.current
    var playerCount by remember { mutableStateOf(2) }
    var targetScore by remember { mutableStateOf("101") }
    val names = remember { mutableStateListOf("", "", "", "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "🎲 تطبيق دبش 🎲",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("كم عدد اللاعبين؟", fontSize = 18.sp)

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                (2..4).forEach {
                    Button(
                        onClick = {
                            playerCount = it
                            for (i in names.indices) names[i] = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (playerCount == it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("$it لاعبين")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            repeat(playerCount) { index ->
                OutlinedTextField(
                    value = names[index],
                    onValueChange = { names[index] = it },
                    label = { Text("اسم اللاعب ${index + 1}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = targetScore,
                onValueChange = { targetScore = it },
                label = { Text("النقاط اللازمة للفوز") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.players.clear()
                    names.take(playerCount).forEach { name ->
                        viewModel.addPlayer(if (name.isBlank()) "لاعب" else name)
                    }
                    viewModel.targetScore = targetScore.toIntOrNull() ?: 101
                    navController.navigate("game")
                    focusManager.clearFocus()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("ابدأ اللعب", fontSize = 20.sp)
            }
        }

        // أزرار الوصول للإعدادات والسياسة
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { navController.navigate("settings") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("⚙️ الإعدادات")
            }

            OutlinedButton(
                onClick = { navController.navigate("privacy") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔒 سياسة الخصوصية")
            }
        }
    }
}
