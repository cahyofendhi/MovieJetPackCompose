package com.bcr.moviejetpackcompose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bcr.moviejetpackcompose.R
import androidx.compose.material.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp

private val Montserrat = FontFamily(
    Font(R.font.montserrat_thin, FontWeight.W200),
    Font(R.font.montserrat_light, FontWeight.W300),
    Font(R.font.montserrat_regular, FontWeight.W400),
    Font(R.font.montserrat_bold, FontWeight.W700),
)


val appTypography = Typography(
    defaultFontFamily = Montserrat,
    h4 = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 26.sp,
        color = primaryBlack,
        letterSpacing = 0.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 22.sp,
        color = primaryBlack,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        color = primaryBlack,
        letterSpacing = 0.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        color = primaryBlack,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        color = primaryBlack,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        color = primaryBlack,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = primaryBlack,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = primaryBlack,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        color = primaryBlack,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        color = primaryBlack,
        letterSpacing = 1.sp
    )
)