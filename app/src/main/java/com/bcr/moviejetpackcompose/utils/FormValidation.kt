package com.bcr.moviejetpackcompose.utils

fun emailValidation(email: String): String? {
    val emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
    val isValid = emailRegEx.toRegex().matches(email)
    return if (email.isEmpty()) {
        "Email is required"
    } else if (!isValid) {
        "Email not valid"
    } else {
        null
    }
}

fun passwordValidation(password: String): String? {
    return when {
        password.isEmpty() -> {
            "Password not be empty"
        }
        password.count() < 8 -> {
            "Password atleast 8 character"
        }
        else -> {
            null
        }
    }
}
