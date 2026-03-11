package com.example.todoApplication.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoApplication.data.entity.Todo

@Composable
fun TodoCard(
    todo: Todo,
    onCheckAction: (Int, Boolean) -> Unit,
) {
    val cardShape = RoundedCornerShape(16.dp)
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    val backgroundColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .drawBehind {
                // Convert 16.dp to pixels
                val cornerRadiusPx = with(this) { 16.dp.toPx() }

                // Shadow color with alpha
                val shadowColor = Color.Black.copy(alpha = 0.2f)

                // Draw a rounded rectangle shadow, offset down & right
                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(4.dp.toPx(), 4.dp.toPx()), // shadow offset
                    size = size,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            }
            .border(BorderStroke(1.dp, borderColor), shape = cardShape)
            .clip(cardShape)
            .background(backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp) // space inside the card
        ) {
            Checkbox(
                checked = todo.completedAt != null,
                onCheckedChange = { checked ->
                    onCheckAction(todo.id, checked)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = todo.title,
            )
        }
    }
}