package com.bcr.moviejetpackcompose.utils.extensions

import androidx.annotation.FloatRange

fun Float.configureProgress(@FloatRange(from = 0.0, to = 1.0) startAt: Float): Float {
    val start = (1f - startAt).coerceAtLeast(0f)
    val multiplier = 1f / start
    return (this - start) * multiplier
}