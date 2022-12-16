package com.githukudenis.todoey.ui.todo_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.todoey.R
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
            TodoList(todoList = state, onOpenTodoDetails = onOpenTodoDetails)
        }
    }
}

@Composable
private fun TodoList(
    modifier: Modifier = Modifier,
    todoList: List<TodoEntity>,
    onOpenTodoDetails: (Long) -> Unit
) {
    val context = LocalContext.current

    val listState = rememberLazyListState()
    if (todoList.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = context.getString(R.string.no_tasks_status),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(items = todoList) { item: TodoEntity ->
            TodoCard(
                modifier = modifier,
                todoEntity = item,
                onOpenTodoDetails = onOpenTodoDetails
            )
        }
    }
}