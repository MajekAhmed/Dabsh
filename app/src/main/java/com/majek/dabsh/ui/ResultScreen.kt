package com.majek.dabsh.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.majek.dabsh.utils.shareScore
import com.majek.dabsh.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(navController: NavController, viewModel: GameViewModel) {
    val context = LocalContext.current
    val winner = viewModel.getWinner()
    val loser = viewModel.players.minByOrNull { it.score }
    var visible by remember { mutableStateOf(false) }

    // تأخير ظهور الأنيميشن
    LaunchedEffect(Unit) {
        delay(500)
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(visible = visible, enter = fadeIn() + scaleIn()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "نتيجة الجولة",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "🏆 الفائز هو ${winner?.name ?: "غير معروف"} بإجمالي ${winner?.score ?: 0} نقطة",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4CAF50)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (loser != null && loser != winner) {
                        Text(
                            text = "💔 الخاسر هو ${loser.name} بإجمالي ${loser.score} نقطة",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFF44336)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = {
                            val text = buildString {
                                append("🏆 الفائز هو ${winner?.name ?: "غير معروف"} بإجمالي ${winner?.score ?: 0} نقطة\n")
                                if (loser != null && loser != winner) {
                                    append("💔 الخاسر هو ${loser.name} بإجمالي ${loser.score} نقطة")
                                }
                            }
                            shareScore(context, text)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("📤 شارك النتيجة", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("↩ العودة للرئيسية", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
