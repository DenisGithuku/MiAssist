package com.githukudenis.tasks.ui.add_task

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.core_data.util.UserMessage
import com.githukudenis.tasks.R
import com.githukudenis.tasks.ui.add_task.components.PriorityChip
import com.githukudenis.tasks.ui.add_task.components.TaskInput
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onSaveTask: () -> Unit,
    onNavigateUp: () -> Unit

) {
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
        android.Manifest.permission.SCHEDULE_EXACT_ALARM,
        android.Manifest.permission.POST_NOTIFICATIONS
        )
    )
    val addTaskViewModel: AddTaskViewModel = hiltViewModel()
    val state = addTaskViewModel.state.collectAsStateWithLifecycle().value
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = context.getString(R.string.add_todo_title)
                )
            },
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            navigationIcon = {
                IconButton(
                    onClick = {
                        onNavigateUp()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_android),
                        contentDescription = context.getString(R.string.navigate_up_text)
                    )
                }
            }
        )

        if (state.userMessages.isNotEmpty()) {
            LaunchedEffect(key1 = state.userMessages, key2 = snackbarHostState) {
                val userMessage = state.userMessages[0]
                snackbarHostState.showSnackbar(
                    message = userMessage.message ?: return@LaunchedEffect
                )
                addTaskViewModel.onEvent(AddTaskEvent.DismissUserMessage(userMessage))
            }
        }
        AddTaskScreen(
            priority = state.priority,
            onChangePriority = { newPriority: Priority ->
                addTaskViewModel.onEvent(AddTaskEvent.ChangeTaskPriority(priority = newPriority))
            },
            priorities = state.priorities,
            multiplePermissionsState = permissionState,
            onSaveTask = { todoEntity ->
                addTaskViewModel.onEvent(AddTaskEvent.SaveTask(todoEntity))
                onSaveTask()
            },
            onShowUserMessage = { userMessage ->
                addTaskViewModel.onEvent(AddTaskEvent.ShowUserMessage(userMessage = userMessage))
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AddTaskScreen(
    modifier: Modifier = Modifier,
    priority: Priority,
    onChangePriority: (Priority) -> Unit,
    priorities: List<Priority>,
    multiplePermissionsState: MultiplePermissionsState,
    onSaveTask: (TaskEntity) -> Unit,
    onSetReminder: () -> Unit,
    onShowUserMessage: (UserMessage) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var todoTitle by remember {
        mutableStateOf("")
    }
    var todoDescription by remember {
        mutableStateOf("")
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }

    var reminderEnabled by remember {
        mutableStateOf(false)
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
                todoDescription
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
                PriorityChip(selected = { item == priority }, onSelect = { newPriority -> onChangePriority(newPriority) }, priority = item)
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

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(checked = reminderEnabled, onCheckedChange = { reminderEnabled = it })
            Text(
                text = "Set reminder"
            )
        }

        Button(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = saveButtonEnabled,
            onClick = {
                if (todoTitle.isEmpty() || todoDescription.isEmpty()) {
                    val message = context.getString(R.string.empty_fields)
                    val userMessage = UserMessage(message = message)
                    onShowUserMessage(userMessage)
                } else {
                    val taskEntity = TaskEntity(
                        taskTitle = todoTitle,
                        taskDescription = todoDescription,
                        taskDueTime = pickedTime,
                        taskDueDate = pickedDate,
                        priority = priority
                    )
                        if (reminderEnabled && multiplePermissionsState.allPermissionsGranted) {
                            onSaveTask(taskEntity).also {
                                val calendar = Calendar.getInstance()
                                calendar.set(
                                    Calendar.DATE = pickedDate,

                                )
                                onSetReminder(, String)
                            }
                        } else if (reminderEnabled && !multiplePermissionsState.allPermissionsGranted) {
                            multiplePermissionsState.launchMultiplePermissionRequest()
                        } else {

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
