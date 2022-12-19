package com.githukudenis.todoey.ui.add_task.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.githukudenis.todoey.data.local.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityChip(
    selected: () -> Boolean,
    onSelect: (Priority) -> Unit,
    priority: Priority,
    modifier: Modifier = Modifier
) {
    val label = remember {
        val firstChar = priority.name.first()
        val lettersAfterFirstChar = priority.name.substringAfter(priority.name.first())
            .lowercase()
        firstChar + lettersAfterFirstChar
    }

    InputChip(
        modifier = modifier.padding(6.dp),
        border = InputChipDefaults.inputChipBorder(
            borderWidth = 0.dp,
            selectedBorderWidth = 0.dp
        ),
        selected = selected(),
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = label
            )
        },
        onClick = {
            onSelect(priority)
        }
    )
}