package com.majek.dabsh.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.majek.dabsh.utils.shareScore
import com.majek.dabsh.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val context = LocalContext.current
    val players = viewModel.players
    val winner = viewModel.getWinner()

    var showRoundDialog by remember { mutableStateOf(false) }
    var selectedPlayerIndex by remember { mutableStateOf(-1) }
    var roundInput by remember { mutableStateOf("") }

    LaunchedEffect(winner) {
        if (winner != null) {
            navController.navigate("result")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎯 النقاط",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        players.forEachIndexed { index, player ->
            val isLeader = player == players.maxByOrNull { it.score }
            val borderColor = if (isLeader) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(2.dp, borderColor, shape = MaterialTheme.shapes.medium)
                    .clickable {
                        selectedPlayerIndex = index
                        roundInput = ""
                        showRoundDialog = true
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(player.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        if (isLeader) {
                            Text("🔥 المتصدر", color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Text("${player.score}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("📊 جدول الجولات", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        players.forEach { player ->
            Text("${player.name}: ${player.rounds.joinToString(" + ")} = ${player.score}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.resetGame() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("إعادة الجولة")
            }

            Button(
                onClick = {
                    val text = buildString {
                        players.forEach { append("${it.name}: ${it.score} نقطة\n") }
                    }
                    shareScore(context, text)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("شارك النتيجة")
            }
        }
    }

    if (showRoundDialog && selectedPlayerIndex != -1) {
        AlertDialog(
            onDismissRequest = { showRoundDialog = false },
            title = { Text("إضافة نقاط الجولة") },
            text = {
                OutlinedTextField(
                    value = roundInput,
                    onValueChange = { roundInput = it },
                    label = { Text("النقاط") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val points = roundInput.toIntOrNull()
                    if (points != null) {
                        players[selectedPlayerIndex].rounds.add(points)
                    }
                    showRoundDialog = false
                }) {
                    Text("حفظ")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRoundDialog = false }) {
                    Text("إلغاء")
                }
            }
        )
    }
}
