package com.githukudenis.todoey.ui.todo_list.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.githukudenis.todoey.data.local.TodoEntity
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TodoCard(
    modifier: Modifier = Modifier,
    onOpenTodoDetails: (Long) -> Unit,
    todoEntity: TodoEntity
) {
    val dueDate = remember {
        when (todoEntity.todoDueDate?.dayOfMonth) {
            LocalDate.now().dayOfMonth - 1 -> {
                "Yesterday"
            }

            LocalDate.now().dayOfMonth -> {
                "Today"
            }

            LocalDate.now().dayOfMonth + 1 -> {
                "Tomorrow"
            }

            else -> {
                "${LocalDate.now().month.name} ${LocalDate.now().dayOfMonth}"
            }
        }
    }

    val dueTime = remember {
        "${todoEntity.todoDueTime?.hour}:${todoEntity.todoDueTime?.minute}"
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .clickable { onOpenTodoDetails(todoEntity.todoId ?: return@clickable) }
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = todoEntity.todoTitle,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = todoEntity.todoDescription ?: return,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = modifier
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = CircleShape
                )
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$dueDate, $dueTime",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(
    name = "light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TodoCardPreviewLight() {
    TodoCard(
        onOpenTodoDetails = {},
        todoEntity = TodoEntity(
            todoTitle = "Go hiking with friends",
            todoDescription = "In the evening",
            todoDueDate = LocalDate.now(),
            todoDueTime = LocalTime.now()
        )
    )
}

@Preview(
    name = "dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TodoCardPreviewDark() {
    TodoCard(
        onOpenTodoDetails = {},
        todoEntity = TodoEntity(
            todoTitle = "Go hiking with friends",
            todoDescription = "In the evening",
            todoDueDate = LocalDate.now(),
            todoDueTime = LocalTime.now()
        )
    )
}