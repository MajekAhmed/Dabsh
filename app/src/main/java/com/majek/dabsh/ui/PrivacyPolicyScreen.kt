package com.majek.dabsh.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.core.net.toUri

@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "🔐 سياسة الخصوصية",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
            تطبيق دبش لا يجمع أية بيانات شخصية، ولا يطلب تسجيل دخول. 
            يتم حفظ البيانات محليًا فقط على جهاز المستخدم باستخدام SharedPreferences.
            الأذونات المطلوبة تستخدم فقط لمشاركة النتائج.
            لا يتم تخزين أو مشاركة أية بيانات مع جهات خارجية.
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 26.sp
        )
        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://docs.google.com/document/d/1GUmRayoFBWSOGAGyaAHfRZ78smrcP73k-gcbVl0DTMU/view".toUri()
        )
    }
}
