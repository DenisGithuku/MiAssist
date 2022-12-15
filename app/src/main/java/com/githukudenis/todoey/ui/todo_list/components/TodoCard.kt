package com.githukudenis.todoey.ui.todo_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.githukudenis.todoey.data.local.TodoEntity

@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    todoEntity: TodoEntity
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = todoEntity.todoTitle,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = todoEntity.todoDescription ?: return,
            style = MaterialTheme.typography.labelSmall
        )
    }
}