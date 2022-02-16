package com.bcr.moviejetpackcompose.utils.extensions

import java.text.DecimalFormat


fun Int?.price(): String {
    return if (this == null) {
        "0"
    } else {
        val df = DecimalFormat("#,###.##")
        "$ ${df.format(this)}"
    }
}