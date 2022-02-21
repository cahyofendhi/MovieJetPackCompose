package com.bcr.moviejetpackcompose.utils.extensions

import androidx.compose.foundation.lazy.LazyListState


val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
