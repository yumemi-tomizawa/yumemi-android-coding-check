/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.util.*

/**
 * RepositoryDetailFragment で使う
 */
class RepositoryListViewModel : ViewModel() {
    private val _searchTextFlow = MutableStateFlow("")

    private val _repositoryList = MutableStateFlow<List<RepositoryInfo>>(emptyList())

    val uiState = combine(_repositoryList, _searchTextFlow) { repositoryList, searchText ->
        RepositoryListUiState(
            repositoryList = repositoryList,
            searchText = searchText,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = RepositoryListUiState.InitialValue,
    )


    // 検索結果
    fun searchResults(inputText: String, context: Context) {
        val client = HttpClient(Android)

        viewModelScope.launch {
            val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }

            val jsonBody = JSONObject(response.receive<String>())

            val jsonItems = jsonBody.optJSONArray("items")!!

            val items = mutableListOf<RepositoryInfo>()

            /**
             * アイテムの個数分ループする
             */
            for (i in 0 until jsonItems.length()) {
                val jsonItem = jsonItems.optJSONObject(i)!!
                val name = jsonItem.optString("full_name")
                val ownerIconUrl = jsonItem.optJSONObject("owner")!!.optString("avatar_url")
                val language = jsonItem.optString("language")
                val stargazersCount = jsonItem.optLong("stargazers_count")
                val watchersCount = jsonItem.optLong("watchers_count")
                val forksCount = jsonItem.optLong("forks_conut")
                val openIssuesCount = jsonItem.optLong("open_issues_count")

                items.add(
                    RepositoryInfo(
                        name = name,
                        ownerIconUrl = ownerIconUrl,
                        language = context.getString(R.string.written_language, language),
                        stargazersCount = stargazersCount,
                        watchersCount = watchersCount,
                        forksCount = forksCount,
                        openIssuesCount = openIssuesCount
                    )
                )
            }

            lastSearchDate = Date()

            _repositoryList.value = items
        }
    }

    fun onValueChange(value: String) {
        _searchTextFlow.value = value
    }
}

@Parcelize
data class RepositoryInfo(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable

data class RepositoryListUiState(
    val repositoryList: List<RepositoryInfo>,
    val searchText: String,
) {
    companion object {
        val InitialValue = RepositoryListUiState(
            repositoryList = emptyList(),
            searchText = ""
        )
    }
}
