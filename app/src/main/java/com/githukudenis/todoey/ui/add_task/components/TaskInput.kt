package com.githukudenis.todoey.ui.add_task.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInput(
    modifier: Modifier = Modifier,
    value: () -> String,
    @StringRes hint: Int,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current

    TextField(
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        value = value(),
        onValueChange = { newValue -> onValueChange(newValue) },
        placeholder = {
            Text(
                text = context.getString(hint)
            )
        }
    )
}