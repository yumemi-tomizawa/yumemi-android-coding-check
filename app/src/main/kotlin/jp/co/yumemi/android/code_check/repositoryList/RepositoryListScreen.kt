package jp.co.yumemi.android.code_check.repositoryList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.RepositoryListUiState
import jp.co.yumemi.android.code_check.RepositoryListViewModel

@Composable
fun RepositoryListRoute(
    viewModel: RepositoryListViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    RepositoryListScreen(
        repositoryListUiState = uiState,
        onValueChange = viewModel::onValueChange,
        onSearch = {
            viewModel.searchResults(it, context)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryListScreen(
    repositoryListUiState: RepositoryListUiState,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Android Engineer CodeCheck") },
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            TextField(
                value = repositoryListUiState.searchText,
                onValueChange = onValueChange,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch(repositoryListUiState.searchText) },
                )
            )
            LazyColumn {
                items(
                    items = repositoryListUiState.repositoryList,
                ) { repository ->
                    Text(text = repository.name)
                }
            }
        }
    }
}

@Preview
@Composable
fun RepositoryListScreenPreview() {
    RepositoryListScreen(
        repositoryListUiState = RepositoryListUiState.InitialValue,
        onValueChange = {},
        onSearch = {},
    )
}