package com.bcr.moviejetpackcompose.utils

import android.util.Log
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun getMovieImage(url: String?): String {
    return if (url != null) {
        "http://image.tmdb.org/t/p/w185$url"
    } else {
        ""
    }
}


fun dateFormat(date: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var convertedDate: Date? = null
    var formattedDate: String = ""
    try {
        convertedDate = sdf.parse(date)
        formattedDate = SimpleDateFormat("dd / MM / yyyy").format(convertedDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return formattedDate
}
