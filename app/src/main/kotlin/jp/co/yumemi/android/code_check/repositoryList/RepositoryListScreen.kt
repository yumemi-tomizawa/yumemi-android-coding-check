package jp.co.yumemi.android.code_check.repositoryList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListScreen() {
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

        }
    }
}

@Preview
@Composable
fun RepositoryListScreenPreview() {
    RepositoryListScreen()
}