package com.majek.dabsh.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.majek.dabsh.model.Player

class GameViewModel : ViewModel() {
    // قائمة اللاعبين كـ SnapshotStateList لتحديث الواجهة أوتوماتيكياً
    val players = mutableStateListOf<Player>()

    // الهدف قابل للملاحظة (حتى لو غيرناه من الواجهة)
    var targetScore by mutableStateOf(101)

    fun addPlayer(name: String) {
        players.add(Player(name = if (name.isBlank()) "لاعب" else name))
    }

    // إعادة تهيئة الجولات (لا تحذف اللاعبين أنفسهم)
    fun resetGame() {
        players.forEach { it.rounds.clear() }
    }

    // مساعدة للحذف الكامل للاعبين لو احتجت
    fun clearAllPlayers() {
        players.clear()
    }

    // إضافة نقاط جولة للاعب (يمكن أن تستخدم للـ + و - أيضاً بتمرير delta موجب/سالب)
    fun addRound(index: Int, points: Int) {
        if (index in players.indices) {
            players[index].rounds.add(points)
        }
    }

    // للحفاظ على التوافق مع الكود القديم اللي كان ينادي updateScore(index, delta)
    // الآن ببساطة نضيف round بقيمة delta
    fun updateScore(index: Int, delta: Int) {
        addRound(index, delta)
    }

    // حذف جولة محددة
    fun removeRound(playerIndex: Int, roundIndex: Int) {
        if (playerIndex in players.indices) {
            val rounds = players[playerIndex].rounds
            if (roundIndex in rounds.indices) rounds.removeAt(roundIndex)
        }
    }

    // إرجاع الفائز إذا وصل أي لاعب للهدف
    fun getWinner(): Player? {
        return players.firstOrNull { it.score >= targetScore }
    }
}
