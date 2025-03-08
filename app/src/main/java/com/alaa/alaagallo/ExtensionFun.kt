package com.alaa.alaagallo

import android.net.Uri
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

fun String.encodeUri(): String = Uri.encode(this, "@#&=*+-_.,:!?()/~'%")

fun Modifier.drawDashedBorder(
    color: Color,
    strokeWidth: Float = 3f,
    dashLengths: FloatArray = floatArrayOf(10f, 10f) // Dash pattern (on 10px, off 10px)
) = this.then(
    Modifier.drawBehind {
        drawRoundRect(
            color = color, // Ensure it does not fill inside
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(dashLengths)
            )
        )
    }
)