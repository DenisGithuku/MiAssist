package com.githukudenis.todoey.ui.todo_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.githukudenis.todoey.data.local.TodoEntity

@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    onOpenTodoDetails: (Long) -> Unit,
    todoEntity: TodoEntity
) {
    val textPriority = remember {
        todoEntity.priority.name.lowercase().replaceFirstChar { firstChar -> firstChar.uppercase() }
    }

    Column(
        modifier = modifier.fillMaxWidth().padding(8.dp).clickable { onOpenTodoDetails(todoEntity.todoId ?: return@clickable) },
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = todoEntity.todoTitle,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = todoEntity.todoDescription ?: return,
            style = MaterialTheme.typography.labelMedium
        )
        Box(
            modifier.clip(
                shape = CircleShape
            ).background(MaterialTheme.colorScheme.primaryContainer).padding(vertical = 8.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = textPriority,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}