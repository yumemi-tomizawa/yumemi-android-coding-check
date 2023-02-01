package jp.co.yumemi.android.code_check.repositoryDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import jp.co.yumemi.android.code_check.RepositoryInfo

@Composable
fun RepositoryDetailRoute(
    repositoryInfo: RepositoryInfo
) {
    RepositoryDetailScreen(
        repositoryInfo
    )

}

@Composable
private fun RepositoryDetailScreen(
    repositoryInfo: RepositoryInfo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xffffffff)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = repositoryInfo.ownerIconUrl,
            contentDescription = null,
        ) {
            when (painter.state) {
                AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Error,
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        Text(
            text = repositoryInfo.name,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Text(
            text = repositoryInfo.ownerIconUrl,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Text(text = repositoryInfo.language,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp),
        )
    }
}

@Preview
@Composable
fun RepositoryDetailScreenPreview() {
    RepositoryDetailScreen(
        repositoryInfo = RepositoryInfo(
            name = "",
            ownerIconUrl = "",
            language = "",
            stargazersCount = 0,
            watchersCount = 0,
            forksCount = 0,
            openIssuesCount = 0
        )
    )
}