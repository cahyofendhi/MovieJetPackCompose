package com.bcr.moviejetpackcompose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.ui.theme.blueYoung
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun <T>InfiniteList(
    listState: LazyListState,
    isLoadmore: Boolean,
    isRefresh: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    data: List<T>,
    content: @Composable (Int, T) -> Unit,
    swipeEnabled: Boolean = true) {
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefresh),
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                scale = true,
                backgroundColor = blueYoung,
                shape = CircleShape,
            )
        },
        swipeEnabled = swipeEnabled,
        onRefresh = onRefresh) {
        LazyColumn(state = listState, contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)) {
            items(data.count()) { content(it, data[it]) }
            if (isLoadmore) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = primaryBlack)
                    }
                }
            }
        }
    }

    listState.OnBottomReached(buffer = 2, onLoadMore)
}

@Composable
fun LazyListState.OnBottomReached(
    buffer : Int = 0,
    loadMore : () -> Unit
){
    // Buffer must be positive.
    // Or our list will never reach the bottom.
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                return@derivedStateOf true
            // subtract buffer from the total items
            lastVisibleItem.index >=  layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMore()
            }
    }

}
