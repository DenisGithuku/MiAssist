package com.githukudenis.tasks.ui.task_list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.denisgithuku.tasks.task_list.components.TaskCard
import com.githukudenis.tasks.R
import com.githukudenis.tasks.ui.task_list.components.DropdownItemSelectionChip
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun TaskListScreen(
    snackbarHostState: SnackbarHostState,
    onNewTask: () -> Unit,
    onOpenTodoDetails: (Long) -> Unit
) {
    val taskListViewModel: TaskListViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelState = remember(taskListViewModel.state, lifecycleOwner) {
        taskListViewModel.state.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val screenState by viewModelState.collectAsState(initial = TaskListUiState())
    val context = LocalContext.current
    val showEmptyTasksMessage = remember(screenState) {
        mutableStateOf(screenState.tasks.isEmpty())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            SortOrderTaskSection(
                selectedSortType = screenState.selectedSortType,
                selectedOrderType = screenState.selectedOrderType,
                onChangeSortType = { sortType ->
                    taskListViewModel.onEvent(TaskListEvent.ChangeSortType(sortType))
                },
                onChangeOrderType = { orderType ->
                    taskListViewModel.onEvent(TaskListEvent.ChangeOrderType(orderType))
                }
            )
            FilterTaskSection(
                selectedPriority = screenState.selectedPriority,
                onFilterByPriority = { priority ->
                    taskListViewModel.onEvent(TaskListEvent.ChangePriorityFilter(priority = priority))
                }
            )

            AnimatedContent(targetState = showEmptyTasksMessage.value) { showMessage ->
                if (showMessage) {
                    Text(
                        text = context.getString(R.string.no_tasks_status),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                } else {
                    TaskList(
                        todoList = screenState.tasks,
                        onOpenTodoDetails = onOpenTodoDetails,
                        onToggleCompleteTask = { taskId ->
                            taskListViewModel.onEvent(TaskListEvent.ToggleCompleteTask(taskId))
                        }
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { onNewTask() }
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Add Task",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SortOrderTaskSection(
    modifier: Modifier = Modifier,
    selectedSortType: SortType,
    selectedOrderType: OrderType,
    onChangeSortType: (SortType) -> Unit,
    onChangeOrderType: (OrderType) -> Unit
) {
    val sortTypes = remember {
        listOf(
            SortType.DUE_DATE,
            SortType.DUE_TIME
        )
    }

    val orderTypes = remember {
        listOf(
            OrderType.ASCENDING,
            OrderType.DESCENDING
        )
    }

    var sortTypeExpanded by remember {
        mutableStateOf(false)
    }

    var orderTypeExpanded by remember {
        mutableStateOf(false)
    }

    var formattedOrderType = remember(selectedOrderType) {
        getOrderType(selectedOrderType)
    }

    var formattedSortType = remember(selectedSortType) {
        getSortType(selectedSortType)
    }

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Sort"
        )
        Row(modifier = modifier) {
            DropdownItemSelectionChip(
                selection = formattedSortType,
                expanded = sortTypeExpanded,
                onClick = {
                    sortTypeExpanded = !sortTypeExpanded
                }
            )
            DropdownMenu(expanded = sortTypeExpanded, onDismissRequest = {
                sortTypeExpanded = !sortTypeExpanded
            }) {
                sortTypes.forEachIndexed { _, sortType ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = getSortType(sortType = sortType)
                            )
                        },
                        onClick = {
                            sortTypeExpanded = !sortTypeExpanded
                            onChangeSortType(sortType)
                        }
                    )
                }
            }
            DropdownItemSelectionChip(
                selection = formattedOrderType,
                onClick = {
                    orderTypeExpanded = !orderTypeExpanded
                },
                expanded = orderTypeExpanded
            )
            DropdownMenu(
                expanded = orderTypeExpanded,
                onDismissRequest = {
                    orderTypeExpanded = !orderTypeExpanded
                }
            ) {
                orderTypes.forEachIndexed { _, orderType ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = getOrderType(orderType = orderType)
                            )
                        },
                        onClick = {
                            orderTypeExpanded = !orderTypeExpanded
                            onChangeOrderType(orderType)
                        }
                    )
                }
            }
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
                    },
                    shape = CircleShape
                )
            }
        }
    }
}

@Composable
private fun TaskList(
    modifier: Modifier = Modifier,
    todoList: List<TaskEntity>,
    onToggleCompleteTask: (Long) -> Unit,
    onOpenTodoDetails: (Long) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(items = todoList) { item: TaskEntity ->
            TaskCard(
                modifier = modifier,
                taskEntity = item,
                onOpenTodoDetails = onOpenTodoDetails,
                onToggleCompleteTask = onToggleCompleteTask
            )
            if (todoList.indexOf(item) != todoList.size - 1) {
                Divider()
            }
        }
    }
}

fun getOrderType(orderType: OrderType): String {
    return when (orderType) {
        OrderType.DESCENDING -> "Newer first"
        OrderType.ASCENDING -> "Older first"
    }
}

fun getSortType(sortType: SortType): String {
    return when (sortType) {
        SortType.DUE_DATE -> "Due date"
        SortType.DUE_TIME -> "Due time"
    }
}

@Preview
@Composable
fun SortOrderTaskSectionPreview() {
    SortOrderTaskSection(
        selectedSortType = SortType.DUE_DATE,
        selectedOrderType = OrderType.ASCENDING,
        onChangeSortType = {},
        onChangeOrderType = {}
    )
}

@Preview(name = "night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SortOrderTaskSectionPreviewNight() {
    SortOrderTaskSection(
        selectedSortType = SortType.DUE_TIME,
        selectedOrderType = OrderType.DESCENDING,
        onChangeSortType = {},
        onChangeOrderType = {}
    )
}