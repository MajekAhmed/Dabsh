package com.majek.dabsh.model

import androidx.compose.runtime.mutableStateListOf

data class Player(
    val name: String,
    // استخدمنا mutableStateListOf عشان تكون التعديلات على الجولات قابلة للملاحظة من Compose
    val rounds: MutableList<Int> = mutableStateListOf()
) {
    // مجموع النقاط محسوب من الجولات
    val score: Int
        get() = rounds.sum()
}
