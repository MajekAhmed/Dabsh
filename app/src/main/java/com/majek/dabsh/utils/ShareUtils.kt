package com.majek.dabsh.utils

import android.content.Context
import android.content.Intent

fun shareScore(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "شارك النتيجة عبر:"))
}
