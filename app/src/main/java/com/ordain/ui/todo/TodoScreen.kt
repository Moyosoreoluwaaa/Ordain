package com.ordain.ui.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ordain.R
import com.ordain.domain.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.todo_list_title)) }
            )
        },
        bottomBar = {
            TodoInput(
                newTodoInput = uiState.newTodoInput,
                onNewTodoInputChanged = viewModel::onNewTodoInputChanged,
                onAddTodoClicked = viewModel::onAddTodoClicked
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(uiState.todos, key = { it.id }) { todo ->
                TodoItem(
                    todo = todo,
                    onTodoToggled = { viewModel.onTodoToggled(todo) },
                    onTodoDeleted = { viewModel.onTodoDeleted(todo) }
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onTodoToggled: () -> Unit,
    onTodoDeleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = todo.title)
        }
        Checkbox(
            checked = todo.isCompleted,
            onCheckedChange = { onTodoToggled() }
        )
        IconButton(onClick = onTodoDeleted) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Todo"
            )
        }
    }
}

@Composable
fun TodoInput(
    newTodoInput: String,
    onNewTodoInputChanged: (String) -> Unit,
    onAddTodoClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = newTodoInput,
        onValueChange = onNewTodoInputChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = { Text(stringResource(R.string.add_a_new_task)) },
        trailingIcon = {
            IconButton(onClick = onAddTodoClicked) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo"
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onAddTodoClicked() })
    )
}