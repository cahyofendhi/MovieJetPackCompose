package com.bcr.moviejetpackcompose.utils.extensions

import com.google.gson.Gson
import java.text.DecimalFormat


fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}


