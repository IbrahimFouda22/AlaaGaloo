package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyAccountsAndSaleItem(title: String, icon: Int, onClick: () -> Unit) {
    Box(
        Modifier.width(250.dp).clip(RoundedCornerShape(20.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(20.dp)).pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() })
            },
        contentAlignment = Alignment.Center
    ) {
        Column {
            Spacer(Modifier.height(30.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Image(painter = painterResource(icon), contentDescription = "")
            }
            Spacer(Modifier.height(30.dp))
            Text(
                title,
                modifier = Modifier.fillMaxWidth().background(Color.Black).padding(vertical = 8.dp),
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            )
        }
    }
}