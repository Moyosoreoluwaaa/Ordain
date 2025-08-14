package com.ordain.ui.journaling.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ordain.R
import com.ordain.domain.model.JournalEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalingTemplatesScreen(
    viewModel: JournalTemplatesViewModel = hiltViewModel(),
    onTemplateSelected: (String) -> Unit,
    onEntrySelected: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.journaling_templates_title)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
            ) {
                items(uiState.templates) { template ->
                    JournalTemplateCard(template = template, onClick = { onTemplateSelected(template.id) })
                }
            }

            if (uiState.recentEntries.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.recent_journal_entries_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.recentEntries) { entry ->
                        JournalEntryCard(entry = entry, onClick = { onEntrySelected(entry.id) })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalTemplateCard(template: JournalTemplate, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = template.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = template.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEntryCard(entry: JournalEntry, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = entry.title, style = MaterialTheme.typography.titleMedium)
            Text(text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(entry.timestamp)), style = MaterialTheme.typography.bodySmall)
        }
    }
}


//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun JournalTemplatesScreen(
//    viewModel: JournalTemplatesViewModel = hiltViewModel(),
//    onTemplateSelected: (String) -> Unit
//) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(stringResource(R.string.journaling_templates_title)) }
//            )
//        }
//    ) { paddingValues ->
//        LazyVerticalGrid(
//            columns = GridCells.Adaptive(minSize = 150.dp),
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(uiState.templates) { template ->
//                JournalTemplateCard(
//                    template = template,
//                    onClick = { onTemplateSelected(template.id) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun JournalTemplateCard(
//    template: JournalTemplate,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = template.name,
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 4.dp)
//            )
//            Text(
//                text = template.description,
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}