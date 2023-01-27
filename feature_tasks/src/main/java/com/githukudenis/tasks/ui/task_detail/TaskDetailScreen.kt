@file:OptIn(ExperimentalMaterial3Api::class)

package com.githukudenis.tasks.ui.task_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.core_data.util.UserMessage
import com.githukudenis.tasks.R
import com.githukudenis.tasks.ui.add_task.components.PriorityChip
import com.githukudenis.tasks.ui.add_task.components.TaskInput
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onUpdateTask: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    val taskDetailViewModel: TaskDetailViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenLifecycleAwareState = remember(taskDetailViewModel.state, lifecycleOwner) {
        taskDetailViewModel.state.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val state by screenLifecycleAwareState.collectAsState(initial = TaskDetailUiState())
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        CenterAlignedTopAppBar(title = {
            Text(
                text = context.getString(R.string.todo_detail_title)
            )
        }, actions = {
            IconButton(onClick = {
                taskDetailViewModel.onEvent(TaskDetailEvent.MarkComplete).also {
                    onUpdateTask()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.check_done),
                    contentDescription = context.getString(R.string.mark_complete)
                )
            }
            IconButton(onClick = {
                taskDetailViewModel.onEvent(TaskDetailEvent.DeleteTask).also {
                    onNavigateUp()
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_trash),
                    contentDescription = context.getString(R.string.delete_task)
                )
            }
        }, scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(), navigationIcon = {
            IconButton(onClick = {
                onNavigateUp()
            }) {
                Icon(
                    painter = painterResource(R.drawable.back_android),
                    contentDescription = context.getString(R.string.navigate_up_text)
                )
            }
        })
        if (state.userMessages.isNotEmpty()) {
            LaunchedEffect(key1 = state.userMessages, key2 = snackbarHostState) {
                val userMessage = state.userMessages[0]
                snackbarHostState.showSnackbar(
                    message = userMessage.message ?: return@LaunchedEffect
                )
                taskDetailViewModel.onEvent(TaskDetailEvent.DismissUserMessage(userMessage))
            }
        }

        state.taskDetail?.let { task ->
            TaskDetailScreen(
                taskDetail = task,
                priorities = state.priorities,
                onUpdateTask = { taskEntity ->
                    taskDetailViewModel.onEvent(TaskDetailEvent.UpdateTask(taskEntity))
                    onUpdateTask()
                },
                onShowUserMessage = { userMessage ->
                    taskDetailViewModel.onEvent(TaskDetailEvent.ShowUserMessage(userMessage = userMessage))
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    taskDetail: TaskEntity,
    priorities: List<Priority>,
    onUpdateTask: (TaskEntity) -> Unit,
    onShowUserMessage: (UserMessage) -> Unit
) {
    val context = LocalContext.current

    var todoTitle by remember {
        mutableStateOf(taskDetail.taskTitle)
    }
    var todoDescription by remember {
        mutableStateOf(taskDetail.taskDescription)
    }

    var priority by remember {
        mutableStateOf(taskDetail.priority)
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    var pickedDate by remember {
        mutableStateOf(taskDetail.taskDueDate ?: LocalDate.now())
    }

    var pickedTime by remember {
        mutableStateOf(taskDetail.taskDueTime ?: LocalTime.now())
    }

    val saveButtonEnabled by remember {
        derivedStateOf {
            todoTitle.isNotEmpty()
        }
    }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MMM, yyyy").format(pickedDate)
        }
    }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("HH:mm").format(pickedTime)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TaskInput(
            value = {
                todoTitle
            },
            onValueChange = { newValue ->
                todoTitle = newValue
            },
            hint = R.string.todo_title_hint
        )

        TaskInput(
            value = {
                todoDescription ?: ""
            },
            onValueChange = { newValue ->
                todoDescription = newValue
            },
            hint = R.string.todo_description_hint
        )

        Text(
            text = context.getString(R.string.priority_title),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Start
        )

        LazyRow {
            items(items = priorities, key = { it.name }) { item: Priority ->
                PriorityChip(
                    selected = { item == priority },
                    onSelect = { newPriority -> priority = newPriority },
                    priority = item
                )
            }
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AssistChip(
                onClick = {
                    dateDialogState.show()
                },
                label = {
                    Text(
                        text = formattedDate.toString()
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.calendar),
                        contentDescription = "Day"
                    )
                },
                shape = CircleShape
            )
            AssistChip(
                onClick = {
                    timeDialogState.show()
                },
                label = {
                    Text(
                        text = formattedTime.toString()
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.clock),
                        contentDescription = "Time"
                    )
                },
                shape = CircleShape
            )
        }

        Button(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = saveButtonEnabled,
            onClick = {
                if (todoTitle.isEmpty() || todoDescription?.isEmpty() == true) {
                    val message = context.getString(R.string.empty_fields)
                    val userMessage = UserMessage(message = message)
                    onShowUserMessage(userMessage)
                } else {
                    val taskEntity = TaskEntity(
                        taskTitle = todoTitle,
                        taskDescription = todoDescription,
                        taskDueTime = pickedTime,
                        taskDueDate = pickedDate,
                        priority = priority,
                        taskId = taskDetail.taskId
                    )
                    if (taskDetail == taskEntity) {
                        return@Button
                    } else {
                        onUpdateTask(taskEntity)
                    }
                }
            }
        ) {
            Text(
                text = "Save"
            )
        }
    }

    MaterialDialog(
        shape = MaterialTheme.shapes.extraLarge,
        dialogState = dateDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = pickedDate,
            title = context.getString(R.string.date_dialog_title),
            allowedDateValidator = { date ->
                date.dayOfMonth >= LocalDate.now().dayOfMonth
            }
        ) { date ->
            pickedDate = date
        }
    }

    MaterialDialog(
        shape = MaterialTheme.shapes.extraLarge,
        dialogState = timeDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = pickedTime.plusHours(1),
            title = context.getString(R.string.time_dialog_title)
        ) { time ->
            pickedTime = time
        }
    }
}