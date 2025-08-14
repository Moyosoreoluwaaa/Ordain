package com.ordain.ui.journaling.entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ordain.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalingScreen(
    viewModel: JournalingViewModel = hiltViewModel(),
    onSaveSuccess: () -> Unit,
    onDeleteSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.template?.name ?: stringResource(R.string.new_journal_entry_title)) },
                actions = {
                    if (uiState.entryId != null) {
                        IconButton(onClick = { viewModel.onDeleteClicked(onDeleteSuccess) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_journal_entry)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.onSaveClicked()
                    onSaveSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.save_journal_entry))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = uiState.title,
                onValueChange = viewModel::onTitleChanged,
                label = { Text(stringResource(R.string.journal_entry_title)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = uiState.content,
                onValueChange = viewModel::onContentChanged,
                label = { Text(uiState.template?.contentPrompt ?: stringResource(R.string.journal_entry_content)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}