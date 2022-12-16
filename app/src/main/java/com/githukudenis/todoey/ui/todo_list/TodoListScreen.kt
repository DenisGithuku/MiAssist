package com.githukudenis.todoey.ui.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.todoey.R
import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.ui.todo_list.components.TodoCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun TodoListScreen(
    onNewTask: () -> Unit,
    onOpenTodoDetails: (Long) -> Unit
) {
    val todosListViewModel: TodosListViewModel = hiltViewModel()
    val state by todosListViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNewTask() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(contentPadding)
        ) {
            if (state.todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = context.getString(R.string.no_tasks_status),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
            FilterTaskSection(
                selectedPriority = state.selectedPriority,
                onFilterByPriority = { priority ->
                    todosListViewModel.onEvent(TodoListEvent.ChangePriorityFilter(priority = priority))
                }
            )
            TodoList(todoList = state.todos, onOpenTodoDetails = onOpenTodoDetails)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTaskSection(
    modifier: Modifier = Modifier,
    selectedPriority: Priority,
    onFilterByPriority: (Priority) -> Unit
) {

    val priorities = remember {
        listOf<Priority>(
            Priority.HIGH,
            Priority.LOW,
            Priority.MODERATE
        )
    }

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Priority"
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = priorities, key = { priority -> priority }) { priority ->
                FilterChip(
                    selected = priority == selectedPriority,
                    label = {
                        Text(
                            text = priority.name.lowercase().replaceFirstChar { firstChar ->
                                firstChar.uppercaseChar()
                            }
                        )
                    },
                    onClick = {
                        onFilterByPriority(priority)
                    }

                )
            }
        }
    }
}

@Composable
private fun TodoList(
    modifier: Modifier = Modifier,
    todoList: List<TodoEntity>,
    onOpenTodoDetails: (Long) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(items = todoList) { item: TodoEntity ->
            TodoCard(
                modifier = modifier,
                todoEntity = item,
                OnOpenTodoDetails = onOpenTodoDetails
            )
            if (todoList.indexOf(item) != todoList.size - 1) {
                Divider()
            }
        }
    }
}