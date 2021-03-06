package com.bcr.moviejetpackcompose.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bcr.moviejetpackcompose.core.model.Movie
import com.bcr.moviejetpackcompose.core.viewmodels.SearchViewModel
import com.bcr.moviejetpackcompose.core.viewmodels.SearchViewModelState
import com.bcr.moviejetpackcompose.ui.card.VMovieCard
import com.bcr.moviejetpackcompose.ui.components.BackButton
import com.bcr.moviejetpackcompose.ui.components.InfiniteList
import com.bcr.moviejetpackcompose.ui.components.field.SearchView
import com.bcr.moviejetpackcompose.ui.theme.appTypography
import com.bcr.moviejetpackcompose.ui.theme.primaryBlack
import com.bcr.moviejetpackcompose.ui.theme.white

private lateinit var viewmodel: SearchViewModel
private lateinit var uiState: State<SearchViewModelState>

@Composable
fun SearchScreen(type: String, onBack:() -> Unit, onPressed: (Movie) -> Unit) {
    viewmodel = SearchViewModel(type)
    uiState = viewmodel.uiState.collectAsState()
    viewmodel.searchMovies(false, "a")
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(modifier = Modifier
                    .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Search",
                        style = appTypography.h6.copy(textAlign = TextAlign.Center),
                    )
                }
            },
            navigationIcon = { BackButton(onBack, primaryBlack) },
            actions = { },
            backgroundColor = white,
            elevation = 3.dp
        )
    }, backgroundColor = white) {
        Column() {
            Card(modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
               SearchView(state = textState, onSubmit = {
                   viewmodel.searchMovies(true, textState.value.text)
               })
            }
            ContentSearch(onPressed)
        }
    }
}

@Composable
fun ContentSearch(onPressed: (Movie) -> Unit) {
    val listState = rememberLazyListState()
    InfiniteList(listState = listState,
        onRefresh = {
            viewmodel.searchMovies(true, null)
         },
        onLoadMore = {  viewmodel.getMovies() },
        data = uiState.value.movies,
        content = { _, item ->  VMovieCard(movie = item, onPressed = {
            onPressed(item)
        })},
        isLoadmore = uiState.value.isLoading,
        isRefresh =  uiState.value.isRefresh,
    )

}
