package com.githukudenis.tasks.ui.task_list.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.githukudenis.tasks.R

@Composable
fun DropdownItemSelectionChip(
    modifier: Modifier = Modifier,
    selection: String,
    onClick: () -> Unit,
    expanded: Boolean
) {
    val context = LocalContext.current

    val animateIconRotation by animateFloatAsState(
        targetValue = -180f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        )
    )
    Box(
        modifier = modifier
            .padding(end = 4.dp)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            )
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = selection,
                style = MaterialTheme.typography.labelMedium
            )
            Icon(
                modifier = modifier.graphicsLayer {
                    rotationX = if (expanded) animateIconRotation else 0f
                },
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = context.getString(if (expanded) R.string.collpase else R.string.expand)
            )
        }
    }
}